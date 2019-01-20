package pl.edu.agh.reporting.reports.domain.site;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface SiteReportRepository {
    void save(SiteReport siteReport);

    List<SiteReport> findAll();

    List<SiteReport> findAllBetween(Instant start, Instant end);

    Optional<SiteReport> findBySiteName(String siteName);
}
