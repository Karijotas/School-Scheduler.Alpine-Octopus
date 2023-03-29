package lt.itacademy.pom.ModulePagesTests;

import lt.itacademy.pom.SubjectPagesTests.EditSubjectPageTest;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({CreateModulePageTest.class, ArchiveModulePageTest.class, EditSubjectPageTest.class, ModulesPageTest.class})

public class ModuleSuite {
}
