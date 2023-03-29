package lt.itacademy.pom.SchedulePagesTests;

import lt.itacademy.pom.SubjectPagesTests.ArchiveSubjectPageTest;
import lt.itacademy.pom.SubjectPagesTests.CreateSubjectPageTest;
import lt.itacademy.pom.SubjectPagesTests.EditSubjectPageTest;
import lt.itacademy.pom.SubjectPagesTests.SubjectPageTest;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;


@Suite
@SelectClasses({CreateSchedulePageTest.class, EditSchedulePageTest.class, SchedulePageTest.class})

public class ScheduleSuite {


}
