import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import {
  Button,
  Divider, Form, Grid, Icon,
  Input, List, Segment, Select, Table, TextArea
} from "semantic-ui-react";
import { EditMenu } from "../../../Components/EditMenu";
import MainMenu from "../../../Components/MainMenu";

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
  const [roomsChange, setRoomsChange] = useState(true);
  const [teachersChange, setTeachersChange] = useState(true);
  const [modulesChange, setModulesChange] = useState(true);

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


  const [state, setState] = useState("")
  const [nameError, setNameError] = useState("")

  const [descriptionError, setDescriptionError] = useState("")




  const [formValid, setFormValid] = useState(false)


  useEffect(() => {
    if (nameError || descriptionError) {
      setFormValid(false)
    } else {
      setFormValid(true)
    }
  }, [nameError, descriptionError])



  const handleNameInputChange = (e) => {
    subjects.name = e.target.value
    setState(e.target.value);
    validateNameInput(e.target.value);
  };

  const handleDescriptionInputChange = (e) => {
    subjects.description = e.target.value
    setState(e.target.value);
    validateDescriptionInput(e.target.value);
  };

  const validateNameInput = (value) => {
    if (value.length < 2 || value.length > 100) {
      setNameError("Įveskite nuo 2 iki 100 simbolių!")
      if (!value) {
        setNameError("Pavadinimas negali būti tuščias!")
      }
    } else {
      setNameError("")
    }
  };

  const validateDescriptionInput = (value) => {
    if (value.length > 500) {
      setDescriptionError("Aprašymas negali viršyti 500 simbolių!")
    } else {
      setDescriptionError("")
    }
  };

  useEffect(() => {
    fetch("/alpine-octopus/api/v1/subjects/" + params.id)
      .then((response) => response.json())
      .then(setSubjects);
  }, [active, params]);

  const applyResult = () => {
    setActive(true);
  };

  // const fetchModulesInSubject = async () => {
  //     fetch(`/api/v1/subjects/${subjectId}/modules`)
  //         .then(response => response.json())
  //         .then(jsonResponse => setModulesInSubjects(jsonResponse));
  // };

  const getModulesInSubjects = () => {
    fetch(`/alpine-octopus/api/v1/subjects/${params.id}/modules`)
      .then((response) => response.json())
      .then(setModulesInSubjects)
      .then(console.log(modulesInSubjects));
  };

  useEffect(() => {
    fetch(`/alpine-octopus/api/v1/subjects/${params.id}/modules`)
      .then((response) => response.json())
      .then(setModulesInSubjects)
      .then(console.log(modulesInSubjects));
  }, [params]);

  const getRoomsInSubjects = () => {
    fetch(`/alpine-octopus/api/v1/subjects/${params.id}/rooms`)
      .then((response) => response.json())
      .then(setRoomsInSubjects)
      .then(console.log(roomsInSubjects));
  };

  useEffect(() => {
    fetch(`/alpine-octopus/api/v1/subjects/${params.id}/rooms`)
      .then((response) => response.json())
      .then(setRoomsInSubjects)
      .then(console.log(roomsInSubjects));
  }, [params]);

  const getTeachersInSubjects = () => {
    fetch(`/alpine-octopus/api/v1/subjects/${params.id}/teachers`)
      .then((response) => response.json())
      .then(setTeachersInSubjects)
      .then(console.log(teachersInSubjects));
  };

  useEffect(() => {
    fetch(`/alpine-octopus/api/v1/subjects/${params.id}/teachers`)
      .then((response) => response.json())
      .then(setTeachersInSubjects)
      .then(console.log(teachersInSubjects));
  }, [params]);

  const updateSubjects = () => {
    fetch("/alpine-octopus/api/v1/subjects/" + params.id, {
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
    fetch(`/alpine-octopus/api/v1/subjects/${params.id}/availableModules`)
      .then((response) => response.json())
      .then((data) =>
        setModules(
          data.map((x) => {
            return { key: x.id, text: x.name, value: x.id };
          })
        )
      );
  }, [modulesInSubjects, module]);

  useEffect(() => {
    fetch(`/alpine-octopus/api/v1/subjects/${params.id}/availableTeachers`)
      .then((response) => response.json())
      .then((data) =>
        setTeachers(
          data.map((x) => {
            return { key: x.id, text: x.name, value: x.id };
          })
        )
      );
  }, [teachersInSubjects, teacher]);

  useEffect(() => {
    fetch(`/alpine-octopus/api/v1/subjects/${params.id}/availableRooms`)
      .then((response) => response.json())
      .then((data) =>
        setRooms(
          data.map((x) => {
            return { key: x.id, text: x.name, value: x.id };
          })
        )
      );
  }, [roomsInSubjects, room]);

  const addModule = (subjectId, moduleId) => {
    fetch(`/alpine-octopus/api/v1/subjects/${subjectId}/modules/${moduleId}/newModules`, {
      method: "POST",
      header: JSON_HEADERS,
      body: JSON.stringify({
        subjectId,
        module,
      }),
    }).then(getModulesInSubjects)
      .then(setModule(""));
  };

  const removeModule = (subjectId, moduleId) => {
    fetch(`/alpine-octopus/api/v1/subjects/${subjectId}/modules/${moduleId}`, {
      method: "DELETE",
      headers: JSON_HEADERS,
    }).then(getModulesInSubjects);
  };

  const addTeacher = (subjectId, teacherId) => {
    fetch(`/alpine-octopus/api/v1/subjects/${subjectId}/teachers/${teacherId}/newTeachers`, {
      method: "POST",
      header: JSON_HEADERS,
      body: JSON.stringify({
        subjectId,
        teacher,
      }),
    }).then(getTeachersInSubjects)
      .then(setTeacher(""))
      .then(setTeachersChange(false));
  };

  const removeTeacher = (subjectId, teacherId) => {
    fetch(`/alpine-octopus/api/v1/subjects/${subjectId}/teachers/${teacherId}`, {
      method: "DELETE",
      headers: JSON_HEADERS,
    }).then(getTeachersInSubjects)
      .then(setTeachersChange(false));
  };

  const addRoom = (subjectId, roomId) => {
    fetch(`/alpine-octopus/api/v1/subjects/${subjectId}/rooms/${roomId}/newRooms`, {
      method: "POST",
      header: JSON_HEADERS,
      body: JSON.stringify({
        subjectId,
        room,
      }),
    }).then(getRoomsInSubjects)
      .then(setRoom(""));
  };

  const removeRoom = (subjectId, roomId) => {
    fetch(`/alpine-octopus/api/v1/subjects/${subjectId}/rooms/${roomId}`, {
      method: "DELETE",
      headers: JSON_HEADERS,
    }).then(getRoomsInSubjects);
  };

  useEffect(() => {
    fetch(`/alpine-octopus/api/v1/subjects/${params.id}/modules`)
      .then((response) => response.json())
      .then(setModulesInSubjects)
      .then(console.log(modulesInSubjects));
  }, [params]);

  useEffect(() => {
    fetch(`/alpine-octopus/api/v1/subjects/${params.id}/teachers`)
      .then((response) => response.json())
      .then(setTeachersInSubjects)
      .then(console.log(teachersInSubjects));
  }, [params]);

  useEffect(() => {
    fetch(`/alpine-octopus/api/v1/subjects/${params.id}/rooms`)
      .then((response) => response.json())
      .then(setRoomsInSubjects)
      .then(console.log(roomsInSubjects));
  }, [params]);

  return (
    <div>
      <MainMenu />
      <Grid columns={2}>
        <Grid.Column width={2} id="main">
          <EditMenu active="subjects" />
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
                <Table celled>
                  <Table.Header>
                    <Table.Row>
                      <Table.HeaderCell>Dalyko pavadinimas</Table.HeaderCell>
                      <Table.HeaderCell>
                        Paskutinis atnaujinimas
                      </Table.HeaderCell>
                      <Table.HeaderCell>Veiksmai</Table.HeaderCell>
                    </Table.Row>
                  </Table.Header>

                  <Table.Body>
                    <Table.Row>
                      <Table.Cell>{subjects.name}</Table.Cell>
                      <Table.Cell collapsing>
                        {" "}
                        {subjects.modifiedDate}{" "}
                      </Table.Cell>
                      <Table.Cell collapsing>
                        <Button id="details" onClick={editThis}>
                          Redaguoti
                        </Button>
                      </Table.Cell>
                    </Table.Row>
                  </Table.Body>
                </Table>
                <Divider hidden />
                <Table celled>
                  <Table.Header>
                    <Table.Row>
                      <Table.HeaderCell>Aprašymas</Table.HeaderCell>
                    </Table.Row>
                  </Table.Header>

                  <Table.Body>
                    <Table.Row>
                      <Table.Cell>{subjects.description}</Table.Cell>
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
                            Moduliai
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
                            Mokytojai
                          </Table.HeaderCell>
                        </Table.Row>
                      </Table.Header>
                      <Table.Body>
                        {teachersInSubjects.map((teacher) => (
                          <Table.Row key={teacher.id}>
                            <Table.Cell>{teacher.name}</Table.Cell>
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
                            Kabinetai
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
                <Table celled>
                  <Table.Header>
                    <Table.Row>
                      <Table.HeaderCell>Dalyko pavadinimas</Table.HeaderCell>
                      <Table.HeaderCell collapsing width={3}>
                        Paskutinis atnaujinimas
                      </Table.HeaderCell>
                    </Table.Row>
                  </Table.Header>

                  <Table.Body>
                    <Table.Row>
                      <Table.Cell collapsing>
                        {(nameError) && <div style={{ color: "red" }}>{nameError}</div>}
                        <Input
                          value={subjects.name}
                          onChange={(e) => handleNameInputChange(e)}
                        />
                      </Table.Cell>
                      <Table.Cell collapsing>
                        {" "}
                        {subjects.modifiedDate}{" "}
                      </Table.Cell>
                    </Table.Row>
                  </Table.Body>
                </Table>


                <Table celled>
                  <Table.Header>
                    <Table.Row>
                      <Table.HeaderCell>Aprašymas</Table.HeaderCell>
                    </Table.Row>
                  </Table.Header>

                  <Table.Body>
                    <Table.Row>
                      <Table.Cell collapsing>
                        <Form>
                          {(descriptionError) && <div style={{ color: "red" }}>{descriptionError}</div>}
                          <TextArea
                            fluid
                            style={{ minHeight: 60 }}
                            value={subjects.description}
                            onChange={(e) => handleDescriptionInputChange(e)}
                          />

                        </Form>
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
                                      placeholder="Moduliai"
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
                                    id='details'
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
                                  <Table.Cell>{teacher.name}</Table.Cell>
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
                                    id='details'
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
                                    id='details'
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
                <Button id='details' disabled={!formValid} floated="right" primary onClick={updateSubjects}>
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
