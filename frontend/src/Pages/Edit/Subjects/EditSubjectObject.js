import React, { useEffect, useState } from "react";
import {
  Button,
  Divider,
  Icon,
  Input,
  Table,
  Grid,
  Segment,
  Container,
} from "semantic-ui-react";
import { ViewSubjects } from "./ViewSubjects";
import MainMenu from "../../../Components/MainMenu";
import { EditMenu } from "../EditMenu";
import { useParams } from "react-router-dom";

const JSON_HEADERS = {
  "Content-Type": "application/json",
};

export function EditSubjectObject() {
  const params = useParams();

  const [modulesInSubjects, setModulesInSubjects] = useState([]);
  const [roomsInSubjects, setRoomsInSubjects] = useState([]);
  const [teachersInSubjects, setTeachersInSubjects] = useState([]);
  const [hide, setHide] = useState(false);
  const [active, setActive] = useState(true);
  const [error, setError] = useState();
  const [subjectId, setSubjectId] = useState("");
  const [subjects, setSubjects] = useState({
    name: "",
    subjectModules: "",
    description: "",
    createdDate: "",
    modifiedDate: "",
  });

  useEffect(() => {
    fetch("/api/v1/subjects/" + params.id)
      .then((response) => response.json())
      .then(setSubjects);
  }, [params]);

  const applyResult = () => {
    setHide(true);
  };

  // const fetchModulesInSubject = async () => {
  //     fetch(`/api/v1/subjects/${subjectId}/modules`)
  //         .then(response => response.json())
  //         .then(jsonResponse => setModulesInSubjects(jsonResponse));
  // };

  useEffect(() => {
    fetch(`/api/v1/subjects/${params.id}/modules`)
      .then((response) => response.json())
      .then(setModulesInSubjects)
      .then(console.log(modulesInSubjects));
  }, [params]);

  useEffect(() => {
    fetch(`/api/v1/subjects/${params.id}/rooms`)
      .then((response) => response.json())
      .then(setRoomsInSubjects)
      .then(console.log(roomsInSubjects));
  }, [params]);

  useEffect(() => {
    fetch(`/api/v1/subjects/${params.id}/teachers`)
      .then((response) => response.json())
      .then(setTeachersInSubjects)
      .then(console.log(teachersInSubjects));
  }, [params]);

  const updateSubjects = () => {
    fetch("/api/v1/subjects/" + params.id, {
      method: "PUT",
      headers: JSON_HEADERS,
      body: JSON.stringify(subjects),
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
    setSubjects({
      ...subjects,
      [property]: event.target.value,
    });
  };

  const editThis = () => {
    setActive(false);
  };
  // const removeGroup = (id) => {
  //     fetch('/api/v1/groups/' + params.id, {
  //         method: 'DELETE',
  //         headers: JSON_HEADERS
  //     })
  //     .then(() => window.location = listUrl);
  // }

  return (
    <div>
      <MainMenu />
      <Grid columns={2}>
        <Grid.Column width={2} id="main">
          <EditMenu active="groups" />
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
                      <Table.HeaderCell>Dalyko pavadinimas</Table.HeaderCell>
                      <Table.HeaderCell>Aprašymas</Table.HeaderCell>
                      <Table.HeaderCell>
                        Paskutinis atnaujinimas:
                      </Table.HeaderCell>
                      <Table.HeaderCell>Veiksmai</Table.HeaderCell>
                    </Table.Row>
                  </Table.Header>

                  <Table.Body>
                    <Table.Row>
                      <Table.Cell>{subjects.name}</Table.Cell>
                      <Table.Cell>{subjects.description}</Table.Cell>
                      <Table.Cell collapsing>
                        {" "}
                        {subjects.modifiedDate}{" "}
                      </Table.Cell>
                      <Table.Cell collapsing>
                        <Button onClick={editThis}>Redaguoti</Button>
                      </Table.Cell>
                    </Table.Row>
                  </Table.Body>
                </Table>

                <Divider hidden />
                <Grid columns={3} divided>
                  <Grid.Column>
                    <Table width={6}>
                      <Table.Header>
                        <Table.Row>
                          <Table.HeaderCell width={6}>
                            Yra šiuose moduliuose:
                          </Table.HeaderCell>
                        </Table.Row>
                      </Table.Header>
                      <Table.Body>
                        {modulesInSubjects.map((module) => (
                          <Table.Row key={module.id}>
                            <Table.Cell>{module.name}</Table.Cell>
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
                          Dėsto šie mokytojai:
                          </Table.HeaderCell>
                        </Table.Row>
                      </Table.Header>
                      <Table.Body>
                        {teachersInSubjects.map((teacher) => (
                          <Table.Row key={teacher.id}>
                            <Table.Cell>{teacher.name} {teacher.surname}</Table.Cell>
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
                            Vyksta šiose klasėse:
                          </Table.HeaderCell>
                        </Table.Row>
                      </Table.Header>
                      <Table.Body>
                        {roomsInSubjects.map((room) => (
                          <Table.Row key={room.id}>
                            <Table.Cell>{room.name}</Table.Cell>
                          </Table.Row>
                        ))}
                      </Table.Body>
                    </Table>
                  </Grid.Column>
                </Grid>

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
                      <Table.HeaderCell>Dalyko pavadinimas</Table.HeaderCell>
                      <Table.HeaderCell>Aprašymas</Table.HeaderCell>
                      <Table.HeaderCell>
                        Paskutinis atnaujinimas:
                      </Table.HeaderCell>
                      <Table.HeaderCell>Veiksmai</Table.HeaderCell>
                    </Table.Row>
                  </Table.Header>

                  <Table.Body>
                    <Table.Row>
                      <Table.Cell collapsing>
                        <Input
                          value={subjects.name}
                          onChange={(e) => updateProperty("name", e)}
                        />
                      </Table.Cell>
                      <Table.Cell collapsing>
                        <Input
                          placeholder={subjects.description}
                          /*options={yearOptions} value={groups.schoolYear} */ onChange={(
                            e
                          ) => updateProperty("description", e)}
                        />
                      </Table.Cell>
                      {/* <Table.Cell collapsing><Input value={groups.studentAmount} onChange={(e) => updateProperty('studentAmount', e)} />
                        </Table.Cell>
                        <Table.Cell collapsing><Input options={shiftOptions} placeholder={groups.program.id} value={groups.program} onChange={(e) => updateProperty('program', e)} />
                        </Table.Cell>
                        <Table.Cell collapsing><Input options={shiftOptions} placeholder={groups.shift} value={groups.shift} onChange={(e) => updateProperty('shift', e)} /> */}
                      {/* </Table.Cell> */}

                      <Table.Cell collapsing>
                        {" "}
                        {subjects.modifiedDate}{" "}
                      </Table.Cell>

                      <Table.Cell collapsing>
                        <Button onClick={() => setActive(true)}>
                          Atšaukti
                        </Button>
                        <Button primary onClick={updateSubjects}>
                          Atnaujinti
                        </Button>
                      </Table.Cell>
                    </Table.Row>
                  </Table.Body>
                </Table>
                {/* <Button icon labelPosition="left" className="" onClick={() => setHide(true)}><Icon name="arrow left" />Atgal į sarašą</Button> */}
              </div>
            )}

            {hide && (
              <div>
                <ViewSubjects />
              </div>
            )}
          </Segment>
        </Grid.Column>
      </Grid>
    </div>
  );
}
