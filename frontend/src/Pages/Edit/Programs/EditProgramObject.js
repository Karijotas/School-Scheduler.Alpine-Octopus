import React, { useEffect, useState } from "react";
import { useParams } from 'react-router-dom';
import {
  Button, Divider, Form, Grid, Icon, Input, List, Segment, Select, Table, TextArea
} from "semantic-ui-react";
import { EditMenu } from '../../../Components/EditMenu';
import MainMenu from '../../../Components/MainMenu';

const JSON_HEADERS = {
  "Content-Type": "application/json",
};

export function EditProgramObject() {
  const params = useParams();
  const [hide, setHide] = useState(false);
  const [active, setActive] = useState(true);
  const [subjects, setSubjects] = useState([]);
  const [subjectsInProgram, setSubjectsInProgram] = useState([]);
  const [subject, setSubject] = useState("");
  const [subjectHours, setSubjectHours] = useState("");
  const [totalHours, setTotalHours] = useState("");
  const [subjectId, setSubjectId] = useState("");
  const [programName, setProgramName] = useState("");
  const [programDescription, setProgramDescription] = useState("");


  const subjectOptions = [
    { key: 23, value: 2023, text: "2023" },
    { key: 24, value: 2024, text: "2024" },
    { key: 25, value: 2025, text: "2025" },
    { key: 26, value: 2026, text: "2026" },
    { key: 27, value: 2027, text: "2027" },
    { key: 28, value: 2028, text: "2028" },
  ];

  const [error, setError] = useState();

  const [programs, setPrograms] = useState({
    name: "",
    description: "",
    modifiedDate: "",
  });


  useEffect(() => {
    fetch("/scheduler/api/v1/programs/" + params.id)
      .then((response) => response.json())
      .then(setPrograms);
  }, []);

  useEffect(() => {
    fetch(`/scheduler/api/v1/programs/${params.id}/subjects`)
      .then((response) => response.json())
      .then(setSubjectsInProgram)
      .then(console.log(subjectsInProgram));
  }, [params]);

  useEffect(() => {
    fetch(`/scheduler/api/v1/programs/${params.id}/subjects/hours`)
      .then((response) => response.json())
      .then(setTotalHours);
  }, [params]);

  const removeSubject = (programId, subjectId, hours) => {
    fetch(`/scheduler/api/v1/programs/${programId}/subjects/${subjectId}/${hours}`, {
      method: "DELETE",
      headers: JSON_HEADERS,
    }).then(fetch(`/scheduler/api/v1/programs/${params.id}/subjects/hours`)
      .then((response) => response.json())
      .then(setTotalHours));

  };

  const addSubjectAndHours = (programId, subjectId, hours) => {
    fetch(`/scheduler/api/v1/programs/${programId}/subjects/${subjectId}/${hours}/newSubjectsWithHours`, {
      method: "POST",
      header: JSON_HEADERS,
      body: JSON.stringify({
        programId,
        subject,
        subjectHours,
      }),
    }).then(fetch(`/scheduler//api/v1/programs/${params.id}/subjects/hours`)
      .then((response) => response.json())
      .then(setTotalHours));
  };


  useEffect(() => {
    fetch("/scheduler/api/v1/subjects/")
      .then((response) => response.json())
      .then((data) =>
        setSubjects(
          data.map((x) => {
            return { key: x.id, text: x.name, value: x.id };
          })
        )
      );
  }, []);

  const applyResult = () => {
    setHide(true);
  };



  const updatePrograms = () => {
    fetch('/scheduler/api/v1/programs/' + params.id, {
      method: "PATCH",
      headers: JSON_HEADERS,
      body: JSON.stringify({

      }),
    })
      .then(console.log(programs))
      .then(result => {
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
    <div><MainMenu />

      <Grid columns={2} >
        <Grid.Column width={2} id='main'>
          <EditMenu />
        </Grid.Column>

        <Grid.Column textAlign='left' verticalAlign='top' width={13}>
          <Segment id='segment' color='teal'>
            {active && !hide && (
              <div>
                <Table celled color="violet">
                  <Table.Header>
                    <Table.Row>
                      <Table.HeaderCell>Programos pavadinimas</Table.HeaderCell>
                      <Table.HeaderCell>Aprašymas</Table.HeaderCell>
                      <Table.HeaderCell>Paskutinis atnaujinimas</Table.HeaderCell>
                      <Table.HeaderCell>Veiksmai</Table.HeaderCell>
                    </Table.Row>
                  </Table.Header>

                  <Table.Body>
                    <Table.Row>
                      <Table.Cell>{programs.name}</Table.Cell>
                      <Table.Cell>{programs.description}</Table.Cell>

                      <Table.Cell collapsing> {programs.modifiedDate} </Table.Cell>

                      <Table.Cell collapsing>
                        <Button onClick={editThis}>Redaguoti</Button>
                      </Table.Cell>
                    </Table.Row>
                  </Table.Body>
                </Table>
                <Divider hidden />
                <Table>
                  <Table.Header>
                    <Table.Row>
                      <Table.HeaderCell width={6}>Programos dalykai</Table.HeaderCell>
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
                      <Table.Cell><h5>Programos valandų skaičius:</h5></Table.Cell>
                      <Table.Cell><h5>{totalHours} val.</h5></Table.Cell>
                    </Table.Row>
                  </Table.Body>
                </Table>
                <Button
                  icon
                  labelPosition="left"
                  className=""
                  href='#/view/programs'
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
                      <Table.HeaderCell>Programos pavadinimas</Table.HeaderCell>
                      <Table.HeaderCell>Aprašymas</Table.HeaderCell>
                      <Table.HeaderCell>Paskutinis atnaujinimas</Table.HeaderCell>

                    </Table.Row>
                  </Table.Header>

                  <Table.Body>
                    <Table.Row>
                      <Table.Cell collapsing>
                        <Input
                          value={programs.name}
                          onChange={(e, data) => ((updateProperty('name', e)), (setProgramName(data)))}
                        />
                      </Table.Cell>
                      <Table.Cell collapsing>
                        <Form>
                          <TextArea
                            style={{ minHeight: 100, minWidth: 200 }}
                            placeholder={programs.description}
                            value={programs.description}
                            /*options={yearOptions} value={groups.schoolYear} */
                            onChange={(e) => updateProperty('description', e)}
                          />
                        </Form>
                        {console.log(programs.description)}
                        {console.log(programs.description)}
                        {console.log(programs.id)}
                      </Table.Cell>
                      {/* <Table.Cell collapsing><Input value={groups.studentAmount} onChange={(e) => updateProperty('studentAmount', e)} />
                        </Table.Cell>
                        <Table.Cell collapsing><Input options={shiftOptions} placeholder={groups.program.id} value={groups.program} onChange={(e) => updateProperty('program', e)} />
                        </Table.Cell>
                        <Table.Cell collapsing><Input options={shiftOptions} placeholder={groups.shift} value={groups.shift} onChange={(e) => updateProperty('shift', e)} /> */}
                      {/* </Table.Cell> */}

                      <Table.Cell collapsing> {programs.modifiedDate} </Table.Cell>

                    </Table.Row>
                  </Table.Body>
                </Table>
                <Table>
                  <Table.Header>
                    <Table.Row>
                      <Table.HeaderCell width={8}>
                        Dalyko pavadinimas
                      </Table.HeaderCell>
                      <Table.HeaderCell width={8}>Pridėti dalykai</Table.HeaderCell>
                    </Table.Row>
                  </Table.Header>
                  <Divider hidden />
                  <Table.Body>
                    <Table.Row>
                      <Table.Cell>
                        <List textAlign="center" divided verticalAlign="middle">
                          <List.Item >
                            <Form.Group widths="equal">
                              <Form.Field>
                                <Select
                                  options={subjects}
                                  placeholder="Dalykai"
                                  value={subject}
                                  onChange={(e, data) => (setSubject(e.target.value), setSubjectId(data.value))}
                                  onClose={() => console.log(subjectId)}
                                />
                              </Form.Field>
                            </Form.Group>
                            <Divider hidden />
                            <List.Content>
                              <Input
                                placeholder="Valandų skaičius"
                                value={subjectHours}
                                onChange={(e) => setSubjectHours(e.target.value)}
                              />
                            </List.Content>
                            <Divider hidden />
                            <List.Content floated="left">
                              <Button onClick={() => addSubjectAndHours(params.id, subjectId, subjectHours)}>Pridėti</Button>
                            </List.Content>
                          </List.Item>
                        </List>
                      </Table.Cell>
                      <Table.Cell>
                        <Table.Body>
                          {subjectsInProgram.map((subject) => (
                            <Table.Row key={subject.id}>
                              <Table.Cell>{subject.subject.name}</Table.Cell>
                              <Table.Cell>{subject.subjectHours} val.</Table.Cell>
                              <Table.Cell collapsing>
                                <Button
                                  basic
                                  compact
                                  icon="remove"
                                  title="Pašalinti"
                                  onClick={() => removeSubject(params.id, subject.subject.id, subject.subjectHours,)}
                                ></Button>
                              </Table.Cell>

                            </Table.Row>
                          ))}
                          <Table.Row>
                            <Table.Cell><h5>Programos valandų skaičius:</h5></Table.Cell>
                            <Table.Cell><h5>{totalHours} val.</h5></Table.Cell>
                          </Table.Row>
                        </Table.Body>
                      </Table.Cell>
                    </Table.Row>
                  </Table.Body>
                </Table>
                <Button onClick={() => setActive(true)}>Atšaukti</Button>
                <Button floated="right" primary onClick={updatePrograms}>
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