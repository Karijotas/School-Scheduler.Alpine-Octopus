package lt.itacademy.pom.ShiftPagesTests;

import lt.itacademy.pom.SubjectPagesTests.ArchiveSubjectPageTest;
import lt.itacademy.pom.SubjectPagesTests.CreateSubjectPageTest;
import lt.itacademy.pom.SubjectPagesTests.EditSubjectPageTest;
import lt.itacademy.pom.SubjectPagesTests.SubjectPageTest;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;


@Suite
@SelectClasses({CreateShiftPageTest.class, ShiftPageTest.class})

public class ShiftSuite {


}
