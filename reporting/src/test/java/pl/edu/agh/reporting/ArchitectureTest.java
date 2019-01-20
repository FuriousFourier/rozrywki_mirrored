package pl.edu.agh.reporting;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.junit.ArchUnitRunner;
import com.tngtech.archunit.lang.ArchRule;
import org.junit.runner.RunWith;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

@RunWith(ArchUnitRunner.class)
@AnalyzeClasses(packages = "pl.edu.agh.reporting")
public class ArchitectureTest {

    @ArchTest
    public static final ArchRule domainShouldNotDependInfrastructure =
            noClasses().that().resideInAPackage("..domain..")
                    .should().accessClassesThat().resideInAPackage("..infrastructure..");

    @ArchTest
    public static final ArchRule domainShouldNotDependApplication =
            noClasses().that().resideInAPackage("..domain..")
                    .should().accessClassesThat().resideInAPackage("..application..");

    @ArchTest
    public static final ArchRule domainShouldNotDependUi =
            noClasses().that().resideInAPackage("..domain..")
                    .should().accessClassesThat().resideInAPackage("..ui..");

    @ArchTest
    public static final ArchRule applicationShouldNotDependUi =
            noClasses().that().resideInAPackage("..application..")
                    .should().accessClassesThat().resideInAPackage("..ui..");

    @ArchTest
    public static final ArchRule applicationShouldNotDependOnInfra =
            noClasses().that().resideInAPackage("..application..")
                    .should().accessClassesThat().resideInAPackage("..infrastructure..");

    @ArchTest
    public static final ArchRule uiShouldNotDependOnInfra =
            noClasses().that().resideInAPackage("..ui..")
                    .should().accessClassesThat().resideInAPackage("..infrastructure..");

}