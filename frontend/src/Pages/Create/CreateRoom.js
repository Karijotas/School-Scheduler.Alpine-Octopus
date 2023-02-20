import { useState } from "react";
import { Button, Form, Icon, Input, } from "semantic-ui-react";
import { ViewRooms } from "../Edit/EditPages/ViewRooms";


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
      '/api/v1/rooms', {
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
    {!hide && <div className="create-new-room">
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
        <div><Button icon labelPosition="left" className="" onClick={() => setHide(true)}><Icon name="arrow left"/>Atgal</Button>
<Button type='submit' className="controls" primary onClick={createRoom}>Sukurti</Button></div>
      </Form>
    </div>}
    {hide && (<div><ViewRooms /></div>)}
  </div>
  );
}