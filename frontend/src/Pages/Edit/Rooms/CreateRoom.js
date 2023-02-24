import { useState, useEffect } from "react";
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

  //Validation
  const [nameDirty, setNameDirty] = useState(false);
  const [buildingDirty, setBuildingDirty] = useState(false);
  const [descriptionDirty, setDescriptionDirty] = useState(false);
  const [nameError, setNameError] = useState("The name field is required!")
  const [buildingError, setBuildingError] = useState("The building field is required!")
  const [descriptionError, setDescriptionError] = useState("")
  const [formValid, setFormValid] = useState(false)

  useEffect(() => {
    if(nameError || buildingError || descriptionError) {
      setFormValid(false)
    } else {
      setFormValid(true)
    }
  }, [nameError, buildingError, descriptionError])
  

  const blurHandler = (e) => {
    switch (e.target.name){
      case 'name':
        setNameDirty(true);
        break
        case 'building':
          setBuildingDirty(true);
          break
    }
  }

  const nameHandler = (e) => {
    setName(e.target.value)
    if(e.target.value.length <2 || e.target.value.length > 40){
      setNameError("Size must be between 2 and 40 characters!")
      if(!e.target.value){
        setNameError("The name field is required!")
      }
    } else {
      setNameError("")
    }
  }

  const buildingHandler = (e) => {
    setBuilding(e.target.value)
    if(e.target.value.length < 2 || e.target.value.length > 40){
      setBuildingError("Size must be between 2 and 40 characters!")
      if(!e.target.value){
        setBuildingError("The building field is required!")
      }
    } else {
      setBuildingError("")
    }
  }

  const descriptionHandler = (e) => {
    setDescription(e.target.value)
    if(e.target.value.length > 100){
      setDescriptionError("Size must be less than 100 characters!")
    } else {
      setDescriptionError("")
    }
  }

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
          {(nameDirty && nameError) && <div style={{color: "red"}}>{nameError}</div>}
          <input name="name" onBlur={blurHandler} placeholder='Klasės pavadinimas' value={name} onChange={e => nameHandler(e)} />
          
        </Form.Field>
        <Form.Field >
          <label>Pastatas</label>
          {(buildingDirty && buildingError) && <div style={{color: "red"}}>{buildingError}</div>}
          <input name="building" onBlur={blurHandler} placeholder='Pastatas' value={building} onChange={e => buildingHandler(e)} />
        </Form.Field>
        <Form.Field >
          <label>Aprašymas</label>
          {(descriptionError) && <div style={{color: "red"}}>{descriptionError}</div>}
          <input input name="description" onBlur={blurHandler} placeholder='Aprasymas' value={description} onChange={e => descriptionHandler(e)} />
        </Form.Field>
            <div><Button icon labelPosition="left" className="" href='#/view/rooms'><Icon name="arrow left" />Atgal</Button>
              <Button disabled={!formValid} type='submit' className="controls" primary onClick={createRoom}>Sukurti</Button></div>
          </Form>
        </Segment>
      </Grid.Column>

    </Grid>
  </div>
  );
}