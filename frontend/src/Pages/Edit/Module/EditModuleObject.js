import React, { useEffect, useState } from "react";
import { useParams } from 'react-router-dom';
import { Button, Divider, Form, Grid, Icon, Input, List, Segment, Select, Table, TextArea } from "semantic-ui-react";
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




  const [name, setName] = useState('');
  const [description, setDescription] = useState('');
  const [nameError, setNameError] = useState("")
    const [buildingError, setBuildingError] = useState("")
    const [descriptionError, setDescriptionError] = useState("")

    const [formValid, setFormValid] = useState(false)


    useEffect(() => {
        if(nameError || descriptionError) {
          setFormValid(false)
        } else {
          setFormValid(true)
        }
      }, [nameError, descriptionError])

    const handleNameInputChange = (e) => {
        modules.name = e.target.value
        setName(e.target.value);
        validateNameInput(e.target.value);
      };

    const handleDescriptionInputChange = (e) => {
      modules.description = e.target.value
        setDescription(e.target.value);
        validateDescriptionInput(e.target.value);
      };
    
    const validateNameInput = (value) => {
        if (value.length <2 || value.length > 40) {
            setNameError("Įveskite nuo 2 iki 40 simbolių!")
            if(!value){
                setNameError("Pavadinimas negali būti tuščias!")
              } 
        } else {
            setNameError("")
        }
      };

      const validateDescriptionInput = (value) => {
        if (value.length > 100) {
            setDescriptionError("Aprašymas negali viršyti 100 symbolių!")
        } else {
            setDescriptionError("")
        }
      };


  const fetchModuleSubjects = async () => {
    fetch(`/api/v1/modules/${params.id}/subjects`)
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
                      <Table.HeaderCell>Paskutinis atnaujinimas</Table.HeaderCell>
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
                            Dalykai
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
                      <Table.HeaderCell >Paskutinis atnaujinimas</Table.HeaderCell>
                    </Table.Row>
                  </Table.Header>

                  <Table.Body>
                    <Table.Row>
                      <Table.Cell collapsing>
                        <Input
                          value={modules.name}
                          
                          onChange={(e) => handleNameInputChange(e)}
                        />{(nameError) && <div style={{color: "red"}}>{nameError}</div>}
                      </Table.Cell>
                      <Table.Cell collapsing>
                        <Input
                          value={modules.description}
                          onChange={(e) => handleDescriptionInputChange(e)}
                        />{(descriptionError) && <div style={{color: "red"}}>{descriptionError}</div>}
                      </Table.Cell>
                      <Table.Cell collapsing> {modules.modifiedDate} </Table.Cell>
                    </Table.Row>
                  </Table.Body>
                </Table>
                <Table>
                  <Table.Header>
                    <Table.Row>
                      <Table.HeaderCell>Aprašymas</Table.HeaderCell>
                    </Table.Row>
                  </Table.Header>
                  <Table.Body>
                    <Table.Row>
                      <Table.Cell>
                        <Form>
                          <TextArea
                            fluid
                            style={{ minHeight: 60 }}
                            value={modules.description}
                            onChange={(e) => updateProperty("description", e)}
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
                                        setSubjects(e.target.value),
                                        setSubjectId(data.value)
                                      )}
                                      onClose={() => console.log(subjectId)}
                                    />
                                  </Form.Field>
                                </Form.Group>
                                <Divider hidden />
                                <List.Content floated="left">
                                  <Button
                                    onClick={() =>
                                      addSubject(params.id, subjectId)
                                    }
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
                <Button  id='details' disabled={!formValid} floated="right" primary onClick={updateModules}>
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
