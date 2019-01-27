package pl.edu.agh.reporting.scheduling.tasks;

import java.io.*;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;

import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pl.edu.agh.reporting.reports.domain.online.OnlinePlayerReport;
import pl.edu.agh.reporting.reports.domain.online.OnlinePlayersRepository;
import pl.edu.agh.reporting.reports.domain.site.SiteReport;
import pl.edu.agh.reporting.reports.domain.site.SiteReportRepository;
import pl.edu.agh.reporting.scheduling.config.MoFFTPConfiguration;

@Service
@Slf4j
public class SendReportsTask {

    private MoFFTPConfiguration FTPConfiguration;
    private OnlinePlayersRepository onlinePlayersRepository;
    private SiteReportRepository siteReportRepository;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd").withZone(ZoneId.systemDefault());

    public SendReportsTask(MoFFTPConfiguration FTPConfiguration, OnlinePlayersRepository onlinePlayersRepository, SiteReportRepository siteReportRepository) {
        this.FTPConfiguration = FTPConfiguration;
        this.onlinePlayersRepository = onlinePlayersRepository;
        this.siteReportRepository = siteReportRepository;
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void uploadReports() {
        Instant now = Instant.now();
        Instant yesterday = now.minus(1, ChronoUnit.DAYS);

        List<OnlinePlayerReport> playerReports = onlinePlayersRepository.findAllBetween(yesterday, now);
        List<SiteReport> siteReports = siteReportRepository.findAllBetween(yesterday, now);

        ByteArrayOutputStream playerOutputStream = new ByteArrayOutputStream();
        ByteArrayOutputStream siteOutputStream = new ByteArrayOutputStream();

        OutputStreamWriter playerWriter = new OutputStreamWriter(playerOutputStream);
        OutputStreamWriter siteWriter = new OutputStreamWriter(siteOutputStream);

        StatefulBeanToCsv<OnlinePlayerReport> playerToCsv = new StatefulBeanToCsvBuilder<OnlinePlayerReport>(playerWriter)
                .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                .build();

        StatefulBeanToCsv<SiteReport> siteToCsv = new StatefulBeanToCsvBuilder<SiteReport>(siteWriter)
                .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                .build();

        try {
            playerToCsv.write(playerReports);
            siteToCsv.write(siteReports);
        } catch (CsvDataTypeMismatchException | CsvRequiredFieldEmptyException e) {
            log.error("Error writing CSV");
        } finally {
            try {
                playerWriter.close();
                siteWriter.close();
            } catch (IOException ignored) {
            }
        }

        FTPClient ftpClient = new FTPClient();

        try {
            ftpClient.connect(FTPConfiguration.address, FTPConfiguration.port);
            ftpClient.login(FTPConfiguration.username, FTPConfiguration.password);

            ByteArrayInputStream playerInputStream = new ByteArrayInputStream(playerOutputStream.toByteArray());
            ftpClient.storeFile(
                    "Player_report_from_" + formatter.format(yesterday) + ".csv",
                    playerInputStream
            );
            playerInputStream.close();

            ByteArrayInputStream siteInputStream = new ByteArrayInputStream(siteOutputStream.toByteArray());
            ftpClient.storeFile(
                    "Site_report_from_" + formatter.format(yesterday) + ".csv",
                    siteInputStream
            );
            siteInputStream.close();

            ftpClient.logout();
        } catch (IOException e) {
            log.error("Can't connect to FTP Server", e);
        } finally {
            try {
                ftpClient.disconnect();
            } catch (IOException ignored) {
            }
        }
    }
}
