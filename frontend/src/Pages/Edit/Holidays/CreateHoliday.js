import { useState, useEffect } from "react";
import { Button, Form, Grid, Icon, Segment, Divider } from "semantic-ui-react";
import { EditMenu } from '../../../Components/EditMenu';
import MainMenu from "../../../Components/MainMenu";
import { useHref } from 'react-router-dom';
import { DatePicker } from "antd";
import dayjs from "dayjs";

const JSON_HEADERS = {
    'Content-Type': 'application/json'
  };

export function CreateHoliday(){
    // const [create, setCreate] = useState()
  const listUrl = useHref('/view/holidays/');
  const [hide, setHide] = useState(false);
  const [name, setName] = useState('');
  const [startDate, setStartDate] = useState('');
  const [endDate, setEndDate] = useState('');

  //Validation
  const [nameDirty, setNameDirty] = useState(false);
//   const [buildingDirty, setBuildingDirty] = useState(false);
  const [nameError, setNameError] = useState("Pavadinimas negali būti tuščias!")
//   const [buildingError, setBuildingError] = useState("Pastatas negali būti tuščias!")
//   const [descriptionError, setDescriptionError] = useState("")
  const [formValid, setFormValid] = useState(false)

  useEffect(() => {
    if (nameError ) {
      setFormValid(false)
    } else {
      setFormValid(true)
    }
  }, [nameError])

  const blurHandler = (e) => {
    switch (e.target.name) {
      case 'name':
        setNameDirty(true);
        break
    }
  }

  const nameHandler = (e) => {
    setName(e.target.value)
    if (e.target.value.length < 2 || e.target.value.length > 100) {
      setNameError("Įveskite nuo 2 iki 100 simbolių!")
      if (!e.target.value) {
        setNameError("Pavadinimas negali būti tuščias!")
      }
    } else {
      setNameError("")
    }
  }

//   const buildingHandler = (e) => {
//     setBuilding(e.target.value)
//     if (e.target.value.length < 2 || e.target.value.length > 100) {
//       setBuildingError("Įveskite nuo 2 iki 100 simbolių!!")
//       if (!e.target.value) {
//         setBuildingError("Pastatas negali būti tuščias!")
//       }
//     } else {
//       setBuildingError("")
//     }
//   }

//   const descriptionHandler = (e) => {
//     setDescription(e.target.value)
//     if (e.target.value.length > 500) {
//       setDescriptionError("Aprašymas negali viršyti 500 simbolių!")
//     } else {
//       setDescriptionError("")
//     }
//   }

  const applyResult = (result) => {
    const clear = () => {
      setHide(true);
    };

    if (result.ok) {
      let info = result.json() 
      .then((jsonResponse) => window.location = listUrl);
    } else {
      window.alert("Nepavyko sukurti: pavadinimas turi būti unikalus!");
    }
  };

  const createHoliday = () => {
    fetch(
      '/api/v1/holidays', {
      method: 'POST',
      headers: JSON_HEADERS,
      body: JSON.stringify({
        name,
        startDate,
        endDate,
      })
    }).then(applyResult) 
;
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
              <label>Atostogų Pavadinimas</label>
              {(nameDirty && nameError) && <div style={{ color: "red" }}>{nameError}</div>}
              <input name="name"  onBlur={blurHandler} placeholder='Atostogų Pavadinimas' value={name} onChange={e => nameHandler(e)} />
            </Form.Field>
            <Form.Field >
              <label>Data nuo</label>
</Form.Field>
</Form>
              {/* {(buildingDirty && buildingError) && <div style={{ color: "red" }}>{buildingError}</div>} */}
              <DatePicker
              className="controls4"
              placeholder="Data nuo"
              onChange={(e) => {
                const newDate = dayjs(e).format("YYYY-MM-DD");
                setStartDate(newDate);
              }}
            />
            
            <Form style={{ paddingTop: '10px' }}>
<Form.Field >
              <label>Data iki</label>
             </Form.Field> 
            </Form>
            

              {/* {(descriptionError) && <div style={{ color: "red" }}>{descriptionError}</div>} */}
              <DatePicker
              className="controls4"
              placeholder="Data iki"
              
              onChange={(e) => {
                const newDate = dayjs(e).format("YYYY-MM-DD");
                setEndDate(newDate);
              }}
            />
            <Divider hidden></Divider>
            <Form>
              <div><Button icon labelPosition="left" className="" href='#/view/holidays'><Icon name="arrow left" />Atgal</Button>
              <Button id='details' type='submit' disabled={!formValid} className="controls" primary onClick={createHoliday}>Sukurti</Button></div>
            </Form>
            
          
        </Segment>
      </Grid.Column>

    </Grid>
  </div>
  );

}