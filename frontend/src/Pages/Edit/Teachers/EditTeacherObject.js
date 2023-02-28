import React, { useEffect, useState } from "react";
import {
  Button,
  Divider,
  Icon,
  Input,
  Table,
  Grid,
  Segment,
  List,
  Form,
  Select,
} from "semantic-ui-react";
import { ViewTeachers } from "./ViewTeachers";
import MainMenu from "../../../Components/MainMenu";
import { EditMenu } from '../../../Components/EditMenu';
import { useParams } from "react-router-dom";

const JSON_HEADERS = {
  "Content-Type": "application/json",
};

export function EditTeacherObject() {
  const params = useParams();
  const [modules, setModules] = useState([]);
  
  const [teacher, setTeacher] = useState("");
  const [teacherId, setTeacherId] = useState("");

  const [hide, setHide] = useState(false);
  const [active, setActive] = useState(true);
  const [error, setError] = useState();
  const [subjectId, setSubjectId] = useState("");
  const [teacherShifts, setTeacherShifts] = useState([]);
  const [teacherSubjects, setTeacherSubjects] = useState([]);
  const [teachers, setTeachers] = useState({
    name: "",
    loginEmail: "",
    contactEmail: "",
    phone: "",
    workHoursPerWeek: "",
    shift: "",
    createdDate: "",
    modifiedDate: "",
  });

  useEffect(() => {
    fetch("/api/v1/teachers/" + params.id)
      .then((response) => response.json())
      .then(setTeachers);
  }, [active, params]);

  const applyResult = () => {
    setActive(true);
  };

  const fetchTeacherShifts = ()  => {
    fetch(`/api/v1/teachers/${params.id}/shifts`)
      .then((response) => response.json())
      .then(setTeacherShifts)
      .then(console.log(teacherShifts));
  };

  useEffect(() => {
    fetch(`/api/v1/teachers/${params.id}/shifts`)
      .then((response) => response.json())
      .then(setTeacherShifts)
      .then(console.log(teacherShifts));
  }, [params]);

  const fetchTeacherSubjects = ()  => {
    fetch(`/api/v1/teachers/${params.id}/subjects`)
      .then((response) => response.json())
      .then(setTeacherSubjects)
      .then(console.log(teacherSubjects));
  };

  useEffect(() => {
    fetch(`/api/v1/teachers/${params.id}/subjects`)
      .then((response) => response.json())
      .then(setTeacherSubjects)
      .then(console.log(teacherSubjects));
  }, [params]);


  const updateTeachers = () => {
    fetch("/api/v1/teachers/" + params.id, {
      method: "PUT",
      headers: JSON_HEADERS,
      body: JSON.stringify(teachers),
    })
      .then((result) => {
        if (!result.ok) {
          setError("Update failed");
        } else {
          setError();
        }
      })
      .then(applyResult);
  };

  const updateProperty = (property, event) => {
    setTeachers({
      ...teachers,
      [property]: event.target.value,
    });
  };

  const editThis = () => {
    setActive(false);
  };

  useEffect(() => {
    fetch("/api/v1/subjects/")
      .then((response) => response.json())
      .then((data) =>
        setModules(
          data.map((x) => {
            return { key: x.id, text: x.name, value: x.id };
          })
        )
      );
  }, []);

//   useEffect(() => {
//     fetch("/api/v1/shifts/")
//       .then((response) => response.json())
//       .then((data) =>
//         setShifts(
//           data.map((x) => {
//             return { key: x.id, text: x.name, value: x.id };
//           })
//         )
//       );
//   }, []);


  return (
    <div>
      <MainMenu />
      <Grid columns={2}>
        <Grid.Column width={2} id="main">
          <EditMenu active="teachers" />
        </Grid.Column>
        <Grid.Column
          floated="left"
          textAlign="left"
          verticalAlign="top"
          width={13}
        >
          <Segment raised color="teal">
            {active && !hide && (
              <div>
                <Table celled color="violet">
                  <Table.Header>
                    <Table.Row>
                      <Table.HeaderCell>Vardas ir pavardė</Table.HeaderCell>
                      <Table.HeaderCell>Telefono nr.</Table.HeaderCell>
                      <Table.HeaderCell>Paskutinis atnaujinimas:</Table.HeaderCell>
                      <Table.HeaderCell>Veiksmai</Table.HeaderCell>
                    </Table.Row>
                  </Table.Header>

                  <Table.Body>
                    <Table.Row>
                      <Table.Cell>{teachers.name}</Table.Cell>
                      <Table.Cell>{teachers.phone}</Table.Cell>
                      <Table.Cell collapsing>
                        {" "}
                        {teachers.modifiedDate}{" "}
                      </Table.Cell>
                      <Table.Cell collapsing>
                        <Button onClick={editThis}>Redaguoti</Button>
                      </Table.Cell>
                    </Table.Row>
                  </Table.Body>
                </Table>
                <Table celled color="violet">
                  <Table.Header>
                    <Table.Row>
                      <Table.HeaderCell>Teams vartotojo vardas</Table.HeaderCell>
                      <Table.HeaderCell>Email</Table.HeaderCell>
                      <Table.HeaderCell>Užimtumas (val. per savaitę)</Table.HeaderCell>
                    </Table.Row>
                  </Table.Header>

                  <Table.Body>
                    <Table.Row>
                      <Table.Cell>{teachers.loginEmail}</Table.Cell>
                      <Table.Cell>{teachers.contactEmail}</Table.Cell>
                      <Table.Cell>{teachers.workHoursPerWeek}</Table.Cell>
                    </Table.Row>
                  </Table.Body>
                </Table>

                <Grid columns={2} divided>
                  <Grid.Column>
                    <Table width={6}>
                      <Table.Header>
                        <Table.Row>
                          <Table.HeaderCell width={6}>
                            Pamainos:
                          </Table.HeaderCell>
                        </Table.Row>
                      </Table.Header>
                      <Table.Body>
                        {teacherShifts.map((shift) => (
                          <Table.Row key={shift.id}>
                            <Table.Cell>{shift.name}</Table.Cell>
                          </Table.Row>
                        ))}
                      </Table.Body>
                    </Table>
                  </Grid.Column>
                  <Grid.Column>
                    <Table width={6}>
                      <Table.Header>
                        <Table.Row>
                          <Table.HeaderCell width={6}>
                            Dalykai:
                          </Table.HeaderCell>
                        </Table.Row>
                      </Table.Header>
                      <Table.Body>
                        {teacherSubjects.map((subject) => (
                          <Table.Row key={subject.id}>
                            <Table.Cell>{subject.name}</Table.Cell>
                          </Table.Row>
                        ))}
                      </Table.Body>
                    </Table>
                  </Grid.Column>
                  </Grid>

                
                <Divider hidden />
                <Button
                  icon
                  labelPosition="left"
                  className=""
                  href="#/view/subjects"
                >
                  <Icon name="arrow left" />
                  Atgal
                </Button>
              </div>
            )}
            {!active && !hide && (
              <div>
                <Table celled color="violet">
                  <Table.Header>
                    <Table.Row>
                    <Table.HeaderCell>Vardas ir pavardė</Table.HeaderCell>
                      <Table.HeaderCell>Teams vartotojo vardas</Table.HeaderCell>
                      <Table.HeaderCell>Email</Table.HeaderCell>
                      <Table.HeaderCell>Telefono nr.</Table.HeaderCell>
                      <Table.HeaderCell>Užimtumas (val. per savaitę)</Table.HeaderCell>
                      <Table.HeaderCell>Paskutinis atnaujinimas:</Table.HeaderCell>
                      <Table.HeaderCell>Veiksmai</Table.HeaderCell>
                    </Table.Row>
                  </Table.Header>

                  <Table.Body>
                    <Table.Row>
                      <Table.Cell collapsing>
                        <Input
                          value={teachers.name}
                          onChange={(e) => updateProperty("name", e)}
                        />
                      </Table.Cell>
                      <Table.Cell collapsing>
                        <Input
                          value={teachers.loginEmail} 
                          onChange={(e) => updateProperty("loginEmail", e)}
                        />
                      </Table.Cell>
                      <Table.Cell collapsing>
                        <Input
                          value={teachers.contactEmail} 
                          onChange={(e) => updateProperty("contactEmail", e)}
                        />
                      </Table.Cell>
                      <Table.Cell collapsing>
                        <Input
                          value={teachers.phone} 
                          onChange={(e) => updateProperty("phone", e)}
                        />
                      </Table.Cell>
                      <Table.Cell collapsing>
                        <Input
                          value={teachers.workHoursPerWeek} 
                          onChange={(e) => updateProperty("workHoursPerWeek", e)}
                        />
                      </Table.Cell>
                      <Table.Cell collapsing>
                        {" "}
                        {teachers.modifiedDate}{" "}
                      </Table.Cell>
                    </Table.Row>
                  </Table.Body>
                </Table>
                <Divider hidden></Divider>
                <Button onClick={() => setActive(true)}>Atšaukti</Button>
                <Button floated="right" primary onClick={updateTeachers}>
                  Atnaujinti
                </Button>
              </div>
            )}
          </Segment>
        </Grid.Column>
      </Grid>
    </div>
  );
}
