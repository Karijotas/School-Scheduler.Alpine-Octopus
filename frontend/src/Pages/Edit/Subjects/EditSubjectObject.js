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
import { ViewSubjects } from "./ViewSubjects";
import MainMenu from "../../../Components/MainMenu";
import { EditMenu } from '../../../Components/EditMenu';
import { useParams } from "react-router-dom";

const JSON_HEADERS = {
  "Content-Type": "application/json",
};

export function EditSubjectObject() {
  const params = useParams();
  const [modules, setModules] = useState([]);
  const [module, setModule] = useState("");
  const [moduleId, setModuleId] = useState("");
  const [teachers, setTeachers] = useState([]);
  const [teacher, setTeacher] = useState("");
  const [teacherId, setTeacherId] = useState("");
  const [rooms, setRooms] = useState([]);
  const [room, setRoom] = useState("");
  const [roomId, setRoomId] = useState("");

  const [modulesInSubjects, setModulesInSubjects] = useState([]);
  const [roomsInSubjects, setRoomsInSubjects] = useState([]);
  const [teachersInSubjects, setTeachersInSubjects] = useState([]);
  const [hide, setHide] = useState(false);
  const [active, setActive] = useState(true);
  const [error, setError] = useState();
  const [subjectId, setSubjectId] = useState("");
  const [subjects, setSubjects] = useState({
    name: "",
    subjectModules: [],
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

  const getModulesInSubjects = ()  => {
    fetch(`/api/v1/subjects/${params.id}/modules`)
      .then((response) => response.json())
      .then(setModulesInSubjects)
      .then(console.log(modulesInSubjects));
  };

  useEffect(() => {
    fetch(`/api/v1/subjects/${params.id}/modules`)
      .then((response) => response.json())
      .then(setModulesInSubjects)
      .then(console.log(modulesInSubjects));
  }, [params]);

  const getRoomsInSubjects = ()  => {
    fetch(`/api/v1/subjects/${params.id}/rooms`)
      .then((response) => response.json())
      .then(setRoomsInSubjects)
      .then(console.log(roomsInSubjects));
  };

  useEffect(() => {
    fetch(`/api/v1/subjects/${params.id}/rooms`)
      .then((response) => response.json())
      .then(setRoomsInSubjects)
      .then(console.log(roomsInSubjects));
  }, [params]);

  const getTeachersInSubjects = ()  => {
    fetch(`/api/v1/subjects/${params.id}/teachers`)
      .then((response) => response.json())
      .then(setTeachersInSubjects)
      .then(console.log(teachersInSubjects));
  };

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

  useEffect(() => {
    fetch("/api/v1/modules/")
      .then((response) => response.json())
      .then((data) =>
        setModules(
          data.map((x) => {
            return { key: x.id, text: x.name, value: x.id };
          })
        )
      );
  }, []);

  useEffect(() => {
    fetch("/api/v1/teachers/")
      .then((response) => response.json())
      .then((data) =>
        setTeachers(
          data.map((x) => {
            return { key: x.id, text: x.name, value: x.id };
          })
        )
      );
  }, []);

  useEffect(() => {
    fetch("/api/v1/rooms/")
      .then((response) => response.json())
      .then((data) =>
        setRooms(
          data.map((x) => {
            return { key: x.id, text: x.name, value: x.id };
          })
        )
      );
  }, []);

  const addModule = (subjectId, moduleId) => {
    fetch(`/api/v1/subjects/${subjectId}/modules/${moduleId}/newModules`, {
      method: "POST",
      header: JSON_HEADERS,
      body: JSON.stringify({
        subjectId,
        module,
      }),
    }).then(getModulesInSubjects);
  };

  const removeModule = (subjectId, moduleId) => {
    fetch(`/api/v1/subjects/${subjectId}/modules/${moduleId}`, {
      method: "DELETE",
      headers: JSON_HEADERS,
    })
    .then(getModulesInSubjects);
  };

  const addTeacher = (subjectId, teacherId) => {
    fetch(`/api/v1/subjects/${subjectId}/teachers/${teacherId}/newTeachers`, {
      method: "POST",
      header: JSON_HEADERS,
      body: JSON.stringify({
        subjectId,
        teacher,
      }),
    }).then(getTeachersInSubjects);
  };

  const removeTeacher = (subjectId, teacherId) => {
    fetch(`/api/v1/subjects/${subjectId}/teachers/${teacherId}`, {
      method: "DELETE",
      headers: JSON_HEADERS,
    }).then(getTeachersInSubjects);
  };

  const addRoom = (subjectId, roomId) => {
    fetch(`/api/v1/subjects/${subjectId}/rooms/${roomId}/newRooms`, {
      method: "POST",
      header: JSON_HEADERS,
      body: JSON.stringify({
        subjectId,
        room,
      }),
    }).then(getRoomsInSubjects);
  };

  const removeRoom = (subjectId, roomId) => {
    fetch(`/api/v1/subjects/${subjectId}/rooms/${roomId}`, {
      method: "DELETE",
      headers: JSON_HEADERS,
    }).then(getRoomsInSubjects);
  };

  useEffect(() => {
    fetch(`/api/v1/subjects/${params.id}/modules`)
      .then((response) => response.json())
      .then(setModulesInSubjects)
      .then(console.log(modulesInSubjects));
  }, [params]);

  useEffect(() => {
    fetch(`/api/v1/subjects/${params.id}/teachers`)
      .then((response) => response.json())
      .then(setTeachersInSubjects)
      .then(console.log(teachersInSubjects));
  }, [params]);

  useEffect(() => {
    fetch(`/api/v1/subjects/${params.id}/rooms`)
      .then((response) => response.json())
      .then(setRoomsInSubjects)
      .then(console.log(roomsInSubjects));
  }, [params]);

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
                            Moduliai:
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
                            Mokytojai:
                          </Table.HeaderCell>
                        </Table.Row>
                      </Table.Header>
                      <Table.Body>
                        {teachersInSubjects.map((teacher) => (
                          <Table.Row key={teacher.id}>
                            <Table.Cell>
                              {teacher.name + " " + teacher.surname}
                            </Table.Cell>
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
                            Kabinetai:
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
                      <Table.HeaderCell>Dalyko pavadinimas</Table.HeaderCell>
                      <Table.HeaderCell>Aprašymas</Table.HeaderCell>
                      <Table.HeaderCell>
                        Paskutinis atnaujinimas:
                      </Table.HeaderCell>
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
                      <Table.Cell collapsing>
                        {" "}
                        {subjects.modifiedDate}{" "}
                      </Table.Cell>
                    </Table.Row>
                  </Table.Body>
                </Table>
                <Grid columns={3}>
                  <Grid.Column>
                    <Table>
                      <Table.Header>
                        <Table.Row>
                          <Table.HeaderCell width={8}>
                            Moduliai
                          </Table.HeaderCell>
                        </Table.Row>
                      </Table.Header>
                      <Table.Body>
                        <Table.Row>
                          <Table.Cell>
                            <Table.Body>
                              {modulesInSubjects.map((module) => (
                                <Table.Row key={module.id}>
                                  <Table.Cell>{module.name}</Table.Cell>
                                  <Table.Cell collapsing>
                                    <Button
                                      basic
                                      compact
                                      icon="remove"
                                      title="Pašalinti"
                                      onClick={() =>
                                        removeModule(params.id, module.id)
                                      }
                                    ></Button>
                                  </Table.Cell>
                                </Table.Row>
                              ))}
                            </Table.Body>
                            <List>
                              <List.Item>
                                <Form.Group widths="equal">
                                  <Form.Field>
                                    <Select
                                      options={modules}
                                      placeholder="Dalykai"
                                      value={module}
                                      onChange={(e, data) => (
                                        setModule(e.target.value),
                                        setModuleId(data.value)
                                      )}
                                      onClose={() => console.log(moduleId)}
                                    />
                                  </Form.Field>
                                </Form.Group>
                                <Divider hidden />
                                <List.Content floated="left">
                                  <Button
                                    onClick={() =>
                                      addModule(params.id, moduleId)
                                    }
                                    // onClose={fetch(
                                    //   `/api/v1/subjects/${params.id}/modules`
                                    // )}
                                  >
                                    Pridėti
                                  </Button>
                                </List.Content>
                              </List.Item>
                            </List>
                          </Table.Cell>
                        </Table.Row>
                      </Table.Body>
                    </Table>
                  </Grid.Column>

                  <Grid.Column>
                    <Table>
                      <Table.Header>
                        <Table.Row>
                          <Table.HeaderCell width={8}>
                            Mokytojai
                          </Table.HeaderCell>
                        </Table.Row>
                      </Table.Header>
                      <Table.Body>
                        <Table.Row>
                          <Table.Cell>
                            <Table.Body>
                              {teachersInSubjects.map((teacher) => (
                                <Table.Row key={teacher.id}>
                                  <Table.Cell>
                                    {teacher.name + " " + teacher.surname}
                                  </Table.Cell>
                                  <Table.Cell collapsing>
                                    <Button
                                      basic
                                      compact
                                      icon="remove"
                                      title="Pašalinti"
                                      onClick={() =>
                                        removeTeacher(params.id, teacher.id)
                                      }
                                    ></Button>
                                  </Table.Cell>
                                </Table.Row>
                              ))}
                            </Table.Body>
                            <List>
                              <List.Item>
                                <Form.Group widths="equal">
                                  <Form.Field>
                                    <Select
                                      options={teachers}
                                      placeholder="Mokytojai"
                                      value={teacher}
                                      onChange={(e, data) => (
                                        setTeacher(e.target.value),
                                        setTeacherId(data.value)
                                      )}
                                      // onClose={() => console.log(moduleId)}
                                    />
                                  </Form.Field>
                                </Form.Group>
                                <Divider hidden />
                                <List.Content floated="left">
                                  <Button
                                    onClick={() =>
                                      addTeacher(params.id, teacherId)
                                    }
                                    // onChange={fetch(
                                    //   `/api/v1/subjects/${params.id}/teachers`
                                    // )}
                                  >
                                    Pridėti
                                  </Button>
                                </List.Content>
                              </List.Item>
                            </List>
                          </Table.Cell>
                        </Table.Row>
                      </Table.Body>
                    </Table>
                  </Grid.Column>

                  <Grid.Column>
                    <Table>
                      <Table.Header>
                        <Table.Row>
                          <Table.HeaderCell width={8}>
                            Kabinetai
                          </Table.HeaderCell>
                        </Table.Row>
                      </Table.Header>
                      <Table.Body>
                        <Table.Row>
                          <Table.Cell>
                            <Table.Body>
                              {roomsInSubjects.map((room) => (
                                <Table.Row key={room.id}>
                                  <Table.Cell>{room.name}</Table.Cell>
                                  <Table.Cell collapsing>
                                    <Button
                                      basic
                                      compact
                                      icon="remove"
                                      title="Pašalinti"
                                      onClick={() =>
                                        removeRoom(params.id, room.id)
                                      }
                                    ></Button>
                                  </Table.Cell>
                                </Table.Row>
                              ))}
                            </Table.Body>
                            <List>
                              <List.Item>
                                <Form.Group widths="equal">
                                  <Form.Field>
                                    <Select
                                      options={rooms}
                                      placeholder="Kabinetai"
                                      value={room}
                                      onChange={(e, data) => (
                                        setRoom(e.target.value),
                                        setRoomId(data.value)
                                      )}
                                      // onClose={() => console.log(roomId)}
                                    />
                                  </Form.Field>
                                </Form.Group>
                                <Divider hidden />
                                <List.Content floated="left">
                                  <Button
                                    onClick={() => addRoom(params.id, roomId)}
                                    // onClose={fetch(
                                    //   `/api/v1/subjects/${params.id}/rooms`
                                    // )}
                                  >
                                    Pridėti
                                  </Button>
                                </List.Content>
                              </List.Item>
                            </List>
                          </Table.Cell>
                        </Table.Row>
                      </Table.Body>
                    </Table>
                  </Grid.Column>
                </Grid>
                <Divider hidden></Divider>
                <Button onClick={() => setActive(true)}>Atšaukti</Button>
                <Button floated="right" primary onClick={updateSubjects}>
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
