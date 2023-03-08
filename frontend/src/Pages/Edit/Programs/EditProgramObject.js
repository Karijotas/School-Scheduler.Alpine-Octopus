import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import {
  Button,
  Divider,
  Form,
  Grid,
  GridColumn,
  Icon,
  Input,
  List,
  Segment,
  Select,
  Table,
  TextArea,
} from "semantic-ui-react";
import { EditMenu } from "../../../Components/EditMenu";
import MainMenu from "../../../Components/MainMenu";

const JSON_HEADERS = {
  "Content-Type": "application/json",
};

export function EditProgramObject() {
  const params = useParams();
  const [active, setActive] = useState(true);
  const [subjects, setSubjects] = useState([]);
  const [subjectsInProgram, setSubjectsInProgram] = useState([]);
  const [subject, setSubject] = useState("");
  const [subjectHours, setSubjectHours] = useState("");
  const [totalHours, setTotalHours] = useState("");
  const [subjectId, setSubjectId] = useState("");
  const [programName, setProgramName] = useState("");
  const [programDescription, setProgramDescription] = useState("");

  const [error, setError] = useState();
  const [programs, setPrograms] = useState({
    name: "",
    description: "",
    modifiedDate: "",
  });

  const [name, setName] = useState("");
  const [description, setDescription] = useState("");
  const [nameError, setNameError] = useState("");

  const [descriptionError, setDescriptionError] = useState("");
  const [hoursError, setHoursError] = useState("");

  const [selectErrorSubject, setSelectErrorSubject] = useState("*Privaloma");

  const [formValid, setFormValid] = useState(false);

  const validateHoursnInput = (value) => {
    if (!/^\d+$/.test(value)) {
      setHoursError("Įveskite tik skaičius");
      if (!value) {
        setHoursError("*Privaloma");
      }
    } else {
      setHoursError("");
    }
  };

  useEffect(() => {
    if (nameError || descriptionError || hoursError) {
      setFormValid(false);
    } else {
      setFormValid(true);
    }
  }, [
    nameError,
    descriptionError, hoursError
  ]);

  const selectSubjectHandler = () => {
    setSelectErrorSubject("");
  };

  const handleNameInputChange = (e) => {
    programs.name = e.target.value;
    setName(e.target.value);
    validateNameInput(e.target.value);
  };

  const handleDescriptionInputChange = (e) => {
    programs.description = e.target.value;
    setDescription(e.target.value);
    validateDescriptionInput(e.target.value);
  };

  const handleHoursInputChange = (e) => {
    setSubjectHours(e.target.value);
    validateHoursnInput(e.target.value);
  };

  const validateNameInput = (value) => {
    if (value.length < 2 || value.length > 100) {
      setNameError("Įveskite nuo 2 iki 100 simbolių!");
      if (!value) {
        setNameError("Pavadinimas negali būti tuščias!");
      }
    } else {
      setNameError("");
    }
  };

  const validateDescriptionInput = (value) => {
    if (value.length > 500) {
      setDescriptionError("Aprašymas negali viršyti 500 simbolių!");
    } else {
      setDescriptionError("");
    }
  };

  useEffect(() => {
    fetch("/api/v1/programs/" + params.id)
      .then((response) => response.json())
      .then(setPrograms);
  }, [totalHours, active, params]);

  useEffect(() => {
    fetch(`/api/v1/programs/${params.id}/subjects`)
      .then((response) => response.json())
      .then(setSubjectsInProgram)
      .then(console.log(subjectsInProgram));
  }, [params, totalHours, subjectHours]);

  const getSubjectsInProgram = () => {
    fetch(`/api/v1/programs/${params.id}/subjects`)
      .then((response) => response.json())
      .then(setSubjectsInProgram)
      .then(console.log(subjectsInProgram));
  };

  useEffect(() => {
    fetch(`/api/v1/programs/${params.id}/subjects/hours`)
      .then((response) => response.json())
      .then(setTotalHours);
  }, [subjectsInProgram]);

  const removeSubject = (programId, subjectId, hours) => {
    fetch(`/api/v1/programs/${programId}/subjects/${subjectId}/${hours}`, {
      method: "DELETE",
      headers: JSON_HEADERS,
    })
      .then(getSubjectsInProgram)
      .then((response) => response.json())
      .then(setTotalHours);
  };

  const addSubjectAndHours = (programId, subjectId, hours) => {
    fetch(
      `/api/v1/programs/${programId}/subjects/${subjectId}/${hours}/newSubjectsWithHours`,
      {
        method: "POST",
        header: JSON_HEADERS,
        body: JSON.stringify({
          programId,
          subject,
          subjectHours,
        }),
      }
    )
      .then(getSubjectsInProgram)
      .then((response) => response.json())
      .then(setSubjectsInProgram)
      .then((response) => response.json())
      .then(setTotalHours)
      .then(updatePrograms)
      .then(setSubject(""))
      .then(setSubjectHours(""));
  };

  useEffect(() => {
    fetch(`/api/v1/programs/${params.id}/availableSubjects`)
      .then((response) => response.json())
      .then((data) =>
        setSubjects(
          data.map((x) => {
            return { key: x.id, text: x.name, value: x.id };
          })
        )
      );
  }, [totalHours]);

  const applyResult = () => {
    setActive(true);
  };

  const updatePrograms = () => {
    fetch("/api/v1/programs/" + params.id, {
      method: "PATCH",
      headers: JSON_HEADERS,
      body: JSON.stringify(programs),
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
    setPrograms({
      ...programs,
      [property]: event.target.value,
    });
  };

  const [updated, setUpdated] = useState();

  useEffect(() => {
    setUpdated(true);
  }, [setUpdated]);

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
          <EditMenu />
        </Grid.Column>
        <Grid.Column textAlign="left" verticalAlign="top" width={13}>
          <Segment id="segment" raised color="teal">
            {active && (
              <div>
                <Table celled>
                  <Table.Header>
                    <Table.Row>
                      <Table.HeaderCell>Programos pavadinimas</Table.HeaderCell>
                      <Table.HeaderCell>
                        Paskutinis atnaujinimas
                      </Table.HeaderCell>
                      <Table.HeaderCell>Veiksmai</Table.HeaderCell>
                    </Table.Row>
                  </Table.Header>

                  <Table.Body>
                    <Table.Row>
                      <Table.Cell>{programs.name}</Table.Cell>

                      <Table.Cell collapsing>
                        {" "}
                        {programs.modifiedDate}{" "}
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
                <Table>
                  <Table.Header>
                    <Table.Row>
                      <Table.HeaderCell>Aprašymas</Table.HeaderCell>
                    </Table.Row>
                  </Table.Header>
                  <Table.Body>
                    <Table.Row>
                      <Table.Cell>{programs.description}</Table.Cell>
                    </Table.Row>
                  </Table.Body>
                </Table>
                <Divider hidden />

                <Grid columns={3}>
                  <Grid.Column>
                    <Table width={6}>
                      <Table.Header>
                        <Table.Row>
                          <Table.HeaderCell >
                            Programos dalykai
                          </Table.HeaderCell>
                          <Table.HeaderCell >
                            
                          </Table.HeaderCell>
                          <Table.HeaderCell ></Table.HeaderCell>
                        </Table.Row>
                      </Table.Header>
                      <Table.Body>
                        {subjectsInProgram.map((subject, index) => (
                          <Table.Row key={index}>
                            <Table.Cell>{subject.subject.name}</Table.Cell>
                            <Table.Cell>{subject.subjectHours} val.</Table.Cell>
                          </Table.Row>
                        ))}
                        <Table.Row>
                          <Table.Cell>
                            <h5>Programos valandų skaičius:</h5>
                          </Table.Cell>
                          <Table.Cell>
                            <h5>{totalHours} val.</h5>
                          </Table.Cell>
                        </Table.Row>
                      </Table.Body>
                    </Table>
                  </Grid.Column>
                </Grid>

                {/* <Table>
                  <Table.Header>
                    <Table.Row>
                      <Table.HeaderCell width={6}>
                        Programos dalykai
                      </Table.HeaderCell>
                      <Table.HeaderCell width={8}></Table.HeaderCell>
                    </Table.Row>
                  </Table.Header>
                  <Table.Body>
                    {subjectsInProgram.map((subject) => (
                      <Table.Row key={subject.id}>
                        <Table.Cell>{subject.subject.name}</Table.Cell>
                        <Table.Cell>{subject.subjectHours} val.</Table.Cell>
                      </Table.Row>
                    ))}
                    <Table.Row>
                      <Table.Cell>
                        <h5>Programos valandų skaičius:</h5>
                      </Table.Cell>
                      <Table.Cell>
                        <h5>{totalHours} val.</h5>
                      </Table.Cell>
                    </Table.Row>
                  </Table.Body>
                </Table> */}
                <Divider hidden />

                <Button
                  icon
                  labelPosition="left"
                  className=""
                  href="#/view/programs"
                >
                  <Icon name="arrow left" />
                  Atgal
                </Button>
              </div>
            )}
            {!active && (
              <div>
                <Table celled>
                  <Table.Header>
                    <Table.Row>
                      <Table.HeaderCell>Programos pavadinimas</Table.HeaderCell>

                      <Table.HeaderCell width={3}>
                        Paskutinis atnaujinimas
                      </Table.HeaderCell>
                    </Table.Row>
                  </Table.Header>

                  <Table.Body>
                    <Table.Row>
                      <Table.Cell>
                        {nameError && (
                          <div style={{ color: "red" }}>{nameError}</div>
                        )}
                        <Input
                          fluid
                          name="name"
                          value={programs.name}
                          onChange={(e) => handleNameInputChange(e)}
                        />
                      </Table.Cell>
                      <Table.Cell collapsing>
                        {" "}
                        {programs.modifiedDate}{" "}
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
                      <Table.Cell>
                        <Form>
                          {descriptionError && (
                            <div style={{ color: "red" }}>
                              {descriptionError}
                            </div>
                          )}
                          <TextArea
                            fluid
                            style={{ minHeight: 120 }}
                            value={programs.description}
                            onChange={(e) => handleDescriptionInputChange(e)}
                          />
                        </Form>
                      </Table.Cell>
                    </Table.Row>
                  </Table.Body>
                </Table>

                <Grid columns={2}>
                  <Grid.Column width={9}>
                    <Table>
                      <Table.Header>
                        <Table.Row>
                          <Table.HeaderCell>
                            Dalyko pavadinimas
                          </Table.HeaderCell>
                          <Table.HeaderCell>
                            Pridėti dalykai
                          </Table.HeaderCell>
                        </Table.Row>
                      </Table.Header>
                      <Table.Body>
                        <Table.Row>
                          <Table.Cell>
                            <Table.Body>
                              {subjectsInProgram.map((subject, index) => (
                                <Table.Row key={index}>
                                  <Table.Cell>
                                    {subject.subject.name}
                                  </Table.Cell>
                                  <Table.Cell>
                                    {subject.subjectHours} val.
                                  </Table.Cell>
                                  <Table.Cell collapsing>
                                    <Button
                                      basic
                                      compact
                                      icon="remove"
                                      title="Pašalinti"
                                      onClick={() =>
                                        removeSubject(
                                          params.id,
                                          subject.subject.id,
                                          subject.subjectHours
                                        )
                                      }
                                    ></Button>
                                  </Table.Cell>
                                </Table.Row>
                              ))}
                              <Table.Row>
                                <Table.Cell>
                                  <h5>Programos valandų skaičius:</h5>
                                </Table.Cell>
                                <Table.Cell>
                                  <h5>{totalHours} val.</h5>
                                </Table.Cell>
                              </Table.Row>
                            </Table.Body>
                            <List>
                              <List.Item>
                                <Form.Group widths="equal">
                                  <Form.Field>
                                    <Select
                                      options={subjects}
                                      placeholder="Dalykai"
                                      value={subject}
                                      onChange={(e, data) => (
                                        setSubject(e.target.value),
                                        setSubjectId(data.value),
                                        selectSubjectHandler(e)
                                      )}
                                      onClose={() => console.log(subjectId)}
                                    />

                                    <Divider hidden />
                                    <List.Content>
                                      {hoursError && (
                                        <div style={{ color: "red" }}>
                                          {hoursError}
                                        </div>
                                      )}
                                      <Input
                                        placeholder="Valandų skaičius"
                                        value={subjectHours}
                                        onChange={(e) =>
                                          handleHoursInputChange(e)
                                        }
                                      />
                                    </List.Content>
                                  </Form.Field>
                                </Form.Group>
                                <Divider hidden />
                                <List.Content floated="left">
                                  <Button
                                    id="details"
                                    onClick={() =>
                                      addSubjectAndHours(
                                        params.id,
                                        subjectId,
                                        subjectHours
                                      )
                                    }
                                  // onClose={fetch(
                                  //   `/api/v1/programs/${params.id}/subjects`
                                  // )}
                                  >
                                    Pridėti
                                  </Button>
                                </List.Content>
                              </List.Item>
                            </List>
                          </Table.Cell>
                          <Table.Cell>
                            <Table.Body>
                              {subjectsInProgram.map((subject) => (
                                <Table.Row key={subject.id}>
                                  <Table.Cell>
                                    {subject.subject.name}
                                  </Table.Cell>
                                  <Table.Cell>
                                    {subject.subjectHours} val.
                                  </Table.Cell>
                                  <Table.Cell collapsing>
                                    <Button
                                      basic
                                      compact
                                      icon="remove"
                                      title="Pašalinti"
                                      onClick={() =>
                                        removeSubject(
                                          params.id,
                                          subject.subject.id,
                                          subject.subjectHours
                                        )
                                      }
                                    ></Button>
                                  </Table.Cell>
                                </Table.Row>
                              ))}
                              <Table.Row>
                                      <Table.Cell>
                                        <h5>Programos valandų skaičius:</h5>
                                      </Table.Cell>
                                      <Table.Cell>
                                        <h5>{totalHours} val.</h5>
                                      </Table.Cell>
                                    </Table.Row>
                            </Table.Body>
                            
                          </Table.Cell>
                        </Table.Row>
                      </Table.Body>
                    </Table>
                  </Grid.Column>
                </Grid>
                <Divider hidden />

                {/* <Table>
                  <Table.Header>
                    <Table.Row>
                      <Table.HeaderCell width={8}>
                        Dalyko pavadinimas
                      </Table.HeaderCell>
                      <Table.HeaderCell width={8}>
                        Pridėti dalykai
                      </Table.HeaderCell>
                    </Table.Row>
                  </Table.Header>
                  <Divider hidden />
                  <Table.Body>
                    <Table.Row>
                      <Table.Cell>
                        <List textAlign="center" divided verticalAlign="middle">
                          <List.Item>
                            <Form.Group widths="equal">
                              <Form.Field>
                                {selectErrorSubject && (
                                  <div style={{ color: "red" }}>
                                    {selectErrorSubject}
                                  </div>
                                )}
                                <Select
                                  options={subjects}
                                  placeholder="Dalykai"
                                  value={subject}
                                  onChange={(e, data) => (
                                    setSubject(e.target.value),
                                    setSubjectId(data.value)
                                    
                                  )}
                                  <Input
                                    placeholder="Valandų skaičius"
                                    value={subjectHours}
                                    onChange={(e) => handleHoursInputChange(e)}
                                  />
                                </List.Content>
                                <Divider hidden />
                                <List.Content floated="left">
                                  <Button
                                    id="details"
                                    onClick={() =>
                                      addSubjectAndHours(
                                        params.id,
                                        subjectId,
                                        subjectHours
                                      )
                                    }
                                    // onClose={fetch(
                                    //   `/api/v1/programs/${params.id}/subjects`
                                    // )}
                                  >
                                    Pridėti
                                  </Button>
                                </List.Content>
                              </List.Item>
                            </List>
                          </Table.Cell>
                          <Table.Cell>
                            <Table.Body>
                              {subjectsInProgram.map((subject) => (
                                <Table.Row key={subject.id}>
                                  <Table.Cell>{subject.subject.name}</Table.Cell>
                                  <Table.Cell>
                                    {subject.subjectHours} val.
                                  </Table.Cell>
                                  <Table.Cell collapsing>
                                    <Button
                                      basic
                                      compact
                                      icon="remove"
                                      title="Pašalinti"
                                      onClick={() =>
                                        removeSubject(
                                          params.id,
                                          subject.subject.id,
                                          subject.subjectHours
                                        )
                                      }
                                    ></Button>
                                  </Table.Cell>
                                </Table.Row>
                              ))}
                              <Table.Row>
                                <Table.Cell>
                                  <h5>Programos valandų skaičius:</h5>
                                </Table.Cell>
                                <Table.Cell>
                                  <h5>{totalHours} val.</h5>
                                </Table.Cell>
                              </Table.Row>
                            </Table.Body>
                          </Table.Cell>
                        </Table.Row>
                      </Table.Body>
                    </Table>
                  </Grid.Column>
                  <Grid.Column></Grid.Column>
                 </Grid> */}

                <Button onClick={() => setActive(true)}>Atšaukti</Button>
                <Button
                  id="details"
                  disabled={!formValid}
                  floated="right"
                  primary
                  onClick={updatePrograms}
                >
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
