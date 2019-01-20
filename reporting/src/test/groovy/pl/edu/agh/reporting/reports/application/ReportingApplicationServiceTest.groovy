package pl.edu.agh.reporting.reports.application

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration
import pl.edu.agh.reporting.ReportingCoreApplication
import spock.lang.Specification

@SpringBootTest
@ContextConfiguration(classes = [ReportingCoreApplication])
class ReportingApplicationServiceTest extends Specification {

    @Autowired
    ReportingApplicationService reportingApplicationService


}
