import { useState } from "react";
import { Button, Form, Grid, Icon, Segment } from "semantic-ui-react";
import MainMenu from "../../../Components/MainMenu";
import { EditMenu } from '../../../Components/EditMenu';


const JSON_HEADERS = {
  'Content-Type': 'application/json'
};



export function CreateRoom() {
  // const [create, setCreate] = useState()
  const [hide, setHide] = useState(false);
  const [name, setName] = useState('');
  const [building, setBuilding] = useState('');
  const [description, setDescription] = useState('');

  const applyResult = (result) => {
    const clear = () => {
      setHide(true)
    }

    if (result.ok) {
      clear();
    } else {
      window.alert("Nepavyko sukurt: " + result.status);
    }
  };

  const createRoom = () => {
    fetch(
      'http://localhost:8081/scheduler/api/v1/rooms', {
      method: 'POST',
      headers: JSON_HEADERS,
      body: JSON.stringify({
        name,
        building,
        description,
      })
    }).then(applyResult)
  };


  return (<div>
    <MainMenu />

    <Grid columns={2} >
      <Grid.Column width={2} id='main'>
        <EditMenu />
      </Grid.Column>

      <Grid.Column floated='left' textAlign='left' verticalAlign='top' width={13}>
        <Segment id='segment' color='teal'>

          <Form >

            <Form.Field >
              <label>Klasės pavadinimas</label>
              <input placeholder='Klasės pavadinimas' value={name} onChange={(e) => setName(e.target.value)} />
            </Form.Field>
            <Form.Field >
              <label>Pastatas</label>
              <input placeholder='Pastatas' value={building} onChange={(e) => setBuilding(e.target.value)} />
            </Form.Field>
            <Form.Field >
              <label>Aprašymas</label>
              <input placeholder='Aprasymas' value={description} onChange={(e) => setDescription(e.target.value)} />
            </Form.Field>
            <div><Button icon labelPosition="left" className="" href='#/view/rooms'><Icon name="arrow left" />Atgal</Button>
              <Button type='submit' className="controls" primary onClick={createRoom}>Sukurti</Button></div>
          </Form>
        </Segment>
      </Grid.Column>

    </Grid>
  </div>
  );
}