
import React, { useEffect, useState } from "react";
import { NavLink } from "react-router-dom";
import {
  Button, ButtonGroup, Confirm,
  Divider, Grid, Icon,
  Input, List, Segment, Table
} from "semantic-ui-react";
import { EditMenu } from "../../../Components/EditMenu";
import MainMenu from "../../../Components/MainMenu";

const JSON_HEADERS = {
  "Content-Type": "application/json",
};

export function ViewTeachers() {
  
  const [open, setOpen] = useState(false);
  const [close, setClose] = useState(false);
  const [active, setActive] = useState();
  const [create, setCreate] = useState("");
  const [teachers, setTeachers] = useState([]);
  const [teachersforPaging, setTeachersForPaging] = useState([]);
  const [nameText, setNameText] = useState("");
  const [shifts, setShifts] = useState([]);
  const [shiftText, setShiftText] = useState("");
  const [activePage, setActivePage] = useState(0);
  const [pagecount, setPageCount] = useState();
  const [teacherSubjects, setTeacherSubjects] = useState([]);
  const [teacherId, setTeacherId] = useState("");
  const [subjectText, setSubjectText] = useState("");

  const fetchFilterTeachers = async () => {
    fetch(`/api/v1/teachers/page/name-filter/${nameText}`)
      .then((response) => response.json())
      .then((jsonRespone) => setTeachers(jsonRespone));
  };

  const fetchTeachersByShifts = async () => {
    fetch(`/api/v1/teachers/page/shift-filter/${shiftText}`)
      .then((response) => response.json())
      .then((jsonResponse) => setTeachers(jsonResponse));
  };

  const fetchFilterTeachersBySubjects = async () => {
    fetch(`/api/v1/teachers/page/subject-filter/${subjectText}?page=` + activePage)
      .then((response) => response.json())
      .then((jsonRespone) => setTeachers(jsonRespone));
  };

  const fetchSingleTeachers = () => {
    fetch("/api/v1/teachers")
      .then((response) => response.json())
      .then((jsonResponse) => setTeachersForPaging(jsonResponse))
      .then(setPageCount(Math.ceil(teachersforPaging.length / 10)));
  };

  const fetchTeachers = async () => {
    fetch(`/api/v1/teachers/page?page=` + activePage)
      .then((response) => response.json())
      .then((jsonRespones) => setTeachers(jsonRespones));
  };

  const removeTeacher = (id) => {
    fetch("/api/v1/teachers/delete/" + id, {
      method: "PATCH",
    }).then(fetchTeachers)
    .then(setOpen(false));
  };

  // const fetchSubjectsById = (teacherId) => {
  //   fetch(`/api/v1/teachers/${teacherId}/subjects?page= + activePage`)
  //     .then((response) => response.json())
  //     .then((jsonResponse) => setTeacherSubjects(jsonResponse));
  // };

  useEffect(() => {
    nameText.length > 0 && !nameText.includes('/') && !nameText.includes('#') && !nameText.includes('.') && !nameText.includes(';') && !nameText.match(new RegExp( /^\s/)) ? 
    fetchFilterTeachers() : (shiftText.length > 0 && !shiftText.includes('/') && !shiftText.includes('#') && !shiftText.includes('.') && !shiftText.includes(';') && !shiftText.match(new RegExp( /^\s/)) ? 
    fetchTeachersByShifts(): (subjectText.length > 0 && !subjectText.includes('/') && !subjectText.includes('#') && !subjectText.includes('.') && !subjectText.includes(';') && !subjectText.match(new RegExp( /^\s/)) ? fetchFilterTeachersBySubjects():fetchTeachers()));
  }, [activePage, nameText, shiftText, subjectText]);



  useEffect(() => {
    if (pagecount !== null) {
      fetchSingleTeachers();
    }
  }, [teachers]);


  const fetchTeacherSubjects = async ()  => {
    fetch(`/api/v1/teachers/${teacherId}/subjects`)
      .then((response) => response.json())
      .then(setTeacherSubjects)
      .then(console.log(teacherSubjects));
  };

  // useEffect(() => {
  //   fetch(`/api/v1/teachers/${teacherId}/subjects`)
  //     .then((response) => response.json())
  //     .then(setTeacherSubjects)
  //     .then(console.log(teacherSubjects));
  // }, [teacherId]);

  // useEffect((teacherId) => {
  //   fetch(`/api/v1/teachers/${teacherId}/subjects`)
  //     .then((response) => response.json())
  //     .then(setTeacherSubjects)
  //     .then(console.log(teacherSubjects));
  // }, [teacherSubjects]);

  return (
    <div>
      <MainMenu />

      <Grid columns={2}>
        <Grid.Column width={2} id="main">
          <EditMenu />
        </Grid.Column>

        <Grid.Column textAlign="left" verticalAlign="top" width={13}>
          <Segment id="segment" color="teal">
            {/* {create && (<div>
                <CreateteacherPage /></div>)}
            {active && (<div className='edit'>
                <EditteacherObject id={active} /></div>)} */}

            {!active && !create && (
              <div id="teacher">
                <Input
                  className="controls1"
                  placeholder="Filtruoti pagal vardą"
                  value={nameText}
                  onChange={(e) => setNameText(e.target.value)}
                />

                <Input
                
                  placeholder="Filtruoti pagal dalykus"
                  value={subjectText}
                  onChange={(e) => setSubjectText(e.target.value)}
                />

                <Input
                className="controls2"
                  placeholder="Filtruoti pagal pamainas"
                  value={shiftText}
                  onChange={(e) => setShiftText(e.target.value)}
                />

                <Button
                  icon
                  id='details'
                  labelPosition="left"
                  primary
                  className="controls"
                  as={NavLink}
                  exact
                  to="/create/teachers"
                >
                  <Icon name="database" />
                  Kurti naują mokytoją
                </Button>
                <Divider horizontal hidden></Divider>

                <Table selectable>
                  <Table.Header>
                    <Table.Row>
                      <Table.HeaderCell>Mokytojo vardas</Table.HeaderCell>
                      <Table.HeaderCell>Dalykai</Table.HeaderCell>
                      <Table.HeaderCell>Valandos per savaitę</Table.HeaderCell>
                      <Table.HeaderCell>Pamainos</Table.HeaderCell>
                      <Table.HeaderCell>Veiksmai</Table.HeaderCell>
                    </Table.Row>
                  </Table.Header>

                  <Table.Body>
                     
                  {/* {console.log(teachers) + "aaaaaa"}   */}
                    {teachers.map((teacher) => (                      
                      <Table.Row key={teacher.id}>                        
                        <Table.Cell>{teacher.name} </Table.Cell>
                        <Table.Cell>
                          <List bulleted>   
                          {/* {console.log(teacher) + "aaaaaa"}                         */}
                            {teacher.teacherSubjects.map((subject) => (
                              <List.Content key={subject.id}>
                                <List.Item>{subject.name}</List.Item>
                              </List.Content>
                            ))}
                            {console.log(teacherSubjects)}
                          </List>
                          {/* <List bulleted>
                            <List.Content>
                              <List.Item>a</List.Item>
                              <List.Item>b</List.Item>
                              <List.Item>c</List.Item>
                            </List.Content>
                          </List> */}
                        </Table.Cell>
                        <Table.Cell>{teacher.workHoursPerWeek}</Table.Cell>
                        <Table.Cell>
                          <List bulleted>
                            {teacher.teacherShifts.map((shift) => (
                              <List.Content key={shift.id}>
                                <List.Item>{shift.name}</List.Item>
                              </List.Content>
                            ))}
                          </List>
                        </Table.Cell>
                        <Table.Cell collapsing>
                          <Button
                          id="icocolor"
                            basic
                            compact
                            icon="eye"
                            title="Peržiūrėti"
                            href={"#/view/teachers/edit/" + teacher.id}
                            onClick={() => setActive(teacher.id)}
                          ></Button>
                          <Button
                          id="icocolor"
                            basic
                            compact
                            title="Suarchyvuoti"
                          icon="archive"
                            onClick={() => setOpen(teacher.id)}
                          ></Button>

                          <Confirm
                            open={open}
                            header="Dėmesio!"
                            content="Ar tikrai norite perkelti į archyvą?"
                          cancelButton="Grįžti atgal"
                          confirmButton="Taip"
                            onCancel={() => setOpen(false)}
                            onConfirm={() => removeTeacher(open)}
                            size="small"
                          />
                        </Table.Cell>
                      </Table.Row>
                    ))}
                  </Table.Body>
                </Table>

                <Divider hidden></Divider>

                <ButtonGroup compact basic>
                  <Button
                    title="Atgal"
                    onClick={() =>
                      setActivePage(
                        activePage <= 0 ? activePage : activePage - 1
                      )
                    }
                    icon
                  >
                    <Icon name="arrow left" />{" "}
                  </Button>
                  {/* {[...Array(pagecount)].map((e, i) => {
                    return (
                      <Button
                        title={i + 1}
                        key={i}
                        active={activePage === i ? true : false}
                        onClick={() => setActivePage(i)}
                      >
                        {i + 1}
                      </Button>
                    );
                  })} */}
                  <Button
                    title="Pirmyn"
                    onClick={() =>
                      setActivePage(
                        activePage >= pagecount - 1
                          ? activePage
                          : activePage + 1
                      )
                    }
                    icon
                  >
                    <Icon name="arrow right" />{" "}
                  </Button>
                </ButtonGroup>
              </div>
            )}
          </Segment>
        </Grid.Column>
      </Grid>
    </div>
  );
}