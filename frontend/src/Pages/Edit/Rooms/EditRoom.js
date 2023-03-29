import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import { Button, Grid, Icon, Input, Segment, Table } from 'semantic-ui-react';
import MainMenu from '../../../Components/MainMenu';
import { EditMenu } from '../../../Components/EditMenu';
import { Form } from 'semantic-ui-react';
import { TextArea } from 'semantic-ui-react';
import { Divider } from 'semantic-ui-react';



const JSON_HEADERS = {
  'Content-Type': 'application/json'
};


export function EditRoom() {

  const params = useParams();

  const [hide, setHide] = useState(false)

  const [active, setActive] = useState(true)

  const [error, setError] = useState();

  const [rooms, setRooms] = useState({
    name: '',
    building: '',
    description: '',
    modifiedDate: '',
  });
  const [name, setName] = useState('');
  const [building, setBuilding] = useState('');
  const [description, setDescription] = useState('');

  const [nameError, setNameError] = useState("")
  const [buildingError, setBuildingError] = useState("")
  const [descriptionError, setDescriptionError] = useState("")

  const [formValid, setFormValid] = useState(false)


  useEffect(() => {
    if (nameError || buildingError || descriptionError) {
      setFormValid(false)
    } else {
      setFormValid(true)
    }
  }, [nameError, buildingError, descriptionError])

  const handleNameInputChange = (e) => {
    rooms.name = e.target.value
    setName(e.target.value);
    validateNameInput(e.target.value);
  };

  const handleBuildingInputChange = (e) => {
    rooms.building = e.target.value
    setBuilding(e.target.value);
    validateBuildingInput(e.target.value);
  };

  const handleDescriptionInputChange = (e) => {
    rooms.description = e.target.value
    setDescription(e.target.value);
    validateDescriptionInput(e.target.value);
  };

  const validateNameInput = (value) => {
    if (value.length < 2 || value.length > 40) {
      setNameError("Įveskite nuo 2 iki 40 simbolių!")
      if (!value) {
        setNameError("Pavadinimas negali būti tuščias!")
      }
    } else {
      setNameError("")
    }
  };

  const validateBuildingInput = (value) => {
    if (value.length < 2 || value.length > 100) {
      setBuildingError("Įveskite nuo 2 iki 40 simbolių!")
      if (!value) {
        setBuildingError("Pastatas negali būti tuščias!")
      }
    } else {
      setBuildingError("")
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
    fetch('/alpine/api/v1/rooms/' + params.id)
      .then(response => response.json())
      .then(setRooms);

  }, [params, active]);




  const applyResult = () => {

    setActive(true);


  }

  const updateRooms = () => {
    fetch('/alpine/api/v1/rooms/' + params.id, {
      method: 'PUT',
      headers: JSON_HEADERS,
      body: JSON.stringify(rooms)
    }).then(result => {
      if (!result.ok) {
        setError('Update failed');
      } else {
        setError();
      }
    }).then(applyResult)
  };

  const updateProperty = (property, event) => {
    setRooms({
      ...rooms,
      [property]: event.target.value
    });
  };



  const editThis = () => {
    setActive(false);
  }


  return (
    <div>
      <MainMenu />

      <Grid columns={2}>
        <Grid.Column width={2} id="main">
          <EditMenu />
        </Grid.Column>

        <Grid.Column textAlign="left" verticalAlign="top" width={13}>
          <Segment id="segment" color="teal">
            {active && !hide && (
              <div>
                <Table celled>
                  <Table.Header>
                    <Table.Row>
                      <Table.HeaderCell>Kabineto pavadinimas</Table.HeaderCell>
                      <Table.HeaderCell>Pastatas</Table.HeaderCell>
                      <Table.HeaderCell>
                        Paskutinis atnaujinimas
                      </Table.HeaderCell>
                      <Table.HeaderCell>Veiksmai</Table.HeaderCell>
                    </Table.Row>
                  </Table.Header>

                  <Table.Body>
                    <Table.Row>
                      <Table.Cell>{rooms.name}</Table.Cell>
                      <Table.Cell>{rooms.building}</Table.Cell>

                      <Table.Cell collapsing> {rooms.modifiedDate} </Table.Cell>

                      <Table.Cell collapsing>
                        <Button onClick={editThis} id="details">
                          Redaguoti
                        </Button>
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
                      <Table.Cell>{rooms.description}</Table.Cell>
                    </Table.Row>
                  </Table.Body>
                </Table>

                <Button
                  icon
                  labelPosition="left"
                  className=""
                  href="#/view/rooms"
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
                      <Table.HeaderCell>Kabineto pavadinimas</Table.HeaderCell>
                      <Table.HeaderCell>Pastatas</Table.HeaderCell>
                      <Table.HeaderCell>
                        Paskutinis atnaujinimas
                      </Table.HeaderCell>
                    </Table.Row>
                  </Table.Header>

                  <Table.Body>
                    <Table.Row>
                      <Table.Cell collapsing>
                        <Input value={rooms.name} onChange={(e) => handleNameInputChange(e)} />
                        {(nameError) && <div style={{ color: "red" }}>{nameError}</div>}
                      </Table.Cell>
                      <Table.Cell collapsing><Input value={rooms.building} onChange={(e) => handleBuildingInputChange(e)} />
                        {(buildingError) && <div style={{ color: "red" }}>{buildingError}</div>}
                      </Table.Cell>
                      <Table.Cell collapsing> {rooms.modifiedDate} </Table.Cell>
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
                          <TextArea
                            fluid
                            style={{ minHeight: 60 }}
                            value={rooms.description} onChange={(e) => handleDescriptionInputChange(e)} />
                          {(descriptionError) && <div style={{ color: "red" }}>{descriptionError}</div>}
                        </Form>
                      </Table.Cell>
                    </Table.Row>
                  </Table.Body>
                </Table>
                <Divider hidden></Divider>
                <Button onClick={() => setActive(true)}>Atšaukti</Button>
                <Button primary className='controls' id='details'  disabled={!formValid} onClick={updateRooms}>Atnaujinti</Button>
              </div>
            )}
          </Segment>
        </Grid.Column>
      </Grid>



    </div>
  );
}