import React, { useEffect, useState } from "react";
import { useParams } from 'react-router-dom';
import { Button, Divider, Form, Grid, Icon, Input, List, Segment, Select, Table } from "semantic-ui-react";
import { EditMenu } from '../../../Components/EditMenu';
import MainMenu from '../../../Components/MainMenu';

const JSON_HEADERS = {
  "Content-Type": "application/json",
};

export function EditModuleObject() {

  const params = useParams();
  const [hide, setHide] = useState(false);
  const [active, setActive] = useState(true);
  const [error, setError] = useState();
  const [moduleSubjects, setModuleSubjects] = useState([]);
  const [moduleId, setModuleId] = useState("");
  const [subjects, setSubjects] = useState([]);
  const [subject, setSubject] = useState("");
  const [subjectId, setSubjectId] = useState("");
  const [modules, setModules] = useState({
    name: "",
    description: "",
    createdDate: "",
    modifiedDate: "",
  });

  const fetchModuleSubjects = async () => {
    fetch(`/scheduler/api/v1/modules/${params.id}/subjects`)
      .then((response) => response.json())
      .then((jsonResponse) => setModuleSubjects(jsonResponse));
  };

  useEffect(() => {
    fetch(`/scheduler/api/v1/modules/${params.id}/subjects`)
      .then((response) => response.json())
      .then(setModuleSubjects)
      .then(console.log(moduleSubjects));
  }, [params]);

  useEffect(() => {
    fetch("/scheduler/api/v1/modules/" + params.id)
      .then((response) => response.json())
      .then(setModules);
  }, [params]);

  const applyResult = () => {
    setActive(true);
  };

  const updateModules = () => {
    fetch("/scheduler/api/v1/modules/update/" + params.id, {
      method: "PUT",
      headers: JSON_HEADERS,
      body: JSON.stringify(modules),
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
    setModules({
      ...modules,
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

  const fetchSingleSubjects = () => {
    fetch("/scheduler/api/v1/subjects")
      .then((response) => response.json())
      .then((jsonResponse) => setSubjects(jsonResponse));
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

  const addSubject = (moduleId, subjectId) => {
    fetch(`/scheduler/api/v1/modules/${moduleId}/subjects/${subjectId}/newSubjects`, {
      method: "POST",
      header: JSON_HEADERS,
      body: JSON.stringify({
        moduleId,
        subject,
      }),
    }).then(fetchModuleSubjects);
  };


  const removeSubject = (moduleId, subjectId) => {
    fetch(`/scheduler/api/v1/modules/${moduleId}/subjects/${subjectId}`, {
      method: "DELETE",
      headers: JSON_HEADERS,
    }).then(fetchModuleSubjects);
  };



  return (
    <div>
      <MainMenu />
      <Grid columns={2} >
        <Grid.Column width={2} id='main'>
          <EditMenu />
        </Grid.Column>
        <Grid.Column textAlign='left' verticalAlign='top' width={13}>
          <Segment id='segment' color='teal'>
            {active && !hide && (
              <div>
                <Table celled>
                  <Table.Header>
                    <Table.Row>
                      <Table.HeaderCell>Modulio pavadinimas</Table.HeaderCell>
                      <Table.HeaderCell>Aprašymas</Table.HeaderCell>
                      <Table.HeaderCell>Paskutinis atnaujinimas:</Table.HeaderCell>
                      <Table.HeaderCell>Veiksmai</Table.HeaderCell>
                    </Table.Row>
                  </Table.Header>

                  <Table.Body>
                    <Table.Row>
                      <Table.Cell>{modules.name}</Table.Cell>
                      <Table.Cell>{modules.description}</Table.Cell>
                      <Table.Cell collapsing> {modules.modifiedDate} </Table.Cell>
                      <Table.Cell collapsing>
                        <Button onClick={editThis} id='details'>Redaguoti</Button>
                      </Table.Cell>
                    </Table.Row>
                  </Table.Body>
                </Table>

                <Grid columns={3} divided>
                  <Grid.Column>
                    <Table width={4}>
                      <Table.Header>
                        <Table.Row>
                          <Table.HeaderCell width={4}>
                            Dalykai:
                          </Table.HeaderCell>
                        </Table.Row>
                      </Table.Header>
                      <Table.Body>
                        {moduleSubjects.map((subject) => (
                          <Table.Row key={subject.id}>
                            <Table.Cell>{subject.name}</Table.Cell>
                          </Table.Row>
                        ))}
                      </Table.Body>
                    </Table>
                  </Grid.Column>
                  </Grid>
                  <Divider hidden />

                {/* <Divider hidden />
          <Table>
            <Table.Header>
              <Table.Row>
                <Table.HeaderCell width={6}>Modulio dalykai</Table.HeaderCell>
              </Table.Row>
            </Table.Header>
            <Table.Body>
              {subjectsInModule.map((subject) => (
                <Table.Row key={subject.id}>
                  <Table.Cell>{subject.subject.name}</Table.Cell>
                </Table.Row>
              ))}
            </Table.Body>
          </Table> */}

                <Button
                  icon
                  labelPosition="left"
                  className=""
                  href='#/view/modules'
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
                      <Table.HeaderCell>Modulio pavadinimas</Table.HeaderCell>
                      <Table.HeaderCell>Aprašymas</Table.HeaderCell>
                      <Table.HeaderCell>Paskutinis atnaujinimas:</Table.HeaderCell>
                    </Table.Row>
                  </Table.Header>

                  <Table.Body>
                    <Table.Row>
                      <Table.Cell collapsing>
                        <Input
                          value={modules.name}
                          onChange={(e) => updateProperty("name", e)}
                        />
                      </Table.Cell>
                      <Table.Cell collapsing>
                        <Input
                          value={modules.description}
                          onChange={(e) => updateProperty("description", e)}
                        />
                      </Table.Cell>
                      <Table.Cell collapsing> {modules.modifiedDate} </Table.Cell>
                    </Table.Row>
                  </Table.Body>
                </Table>
                <Grid columns={3}>
                  <Grid.Column>
                    <Table>
                      <Table.Header>
                        <Table.Row>
                          <Table.HeaderCell width={8}>
                            Dalykai
                          </Table.HeaderCell>
                        </Table.Row>
                      </Table.Header>
                      <Table.Body>
                        <Table.Row>
                          <Table.Cell>
                            <Table.Body>
                              {moduleSubjects.map((subject) => (
                                <Table.Row key={subject.id}>
                                  <Table.Cell>{subject.name}</Table.Cell>
                                  <Table.Cell collapsing>
                                    <Button
                                      basic
                                      compact
                                      icon="remove"
                                      title="Pašalinti"
                                      onClick={() =>
                                        removeSubject(params.id, subject.id)
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
                                      options={subjects}
                                      placeholder="Dalykai"
                                      value={subject}
                                      onChange={(e, data) => (
                                        setSubject(e.target.value),
                                        setSubjectId(data.value)
                                      )}
                                      // onClose={() => console.log(subjectId)}
                                    />
                                  </Form.Field>
                                </Form.Group>
                                <Divider hidden />
                                <List.Content floated="left">
                                  <Button
                                    onClick={() =>
                                      addSubject(params.id, subjectId)
                                    } id='details'
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
                <Button  id='details' floated="right" primary onClick={updateModules}>
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
