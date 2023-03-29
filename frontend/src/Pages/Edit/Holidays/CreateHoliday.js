import { useState, useEffect } from "react";
import { Button, Form, Grid, Icon, Segment, Divider } from "semantic-ui-react";
import { EditMenu } from '../../../Components/EditMenu';
import MainMenu from "../../../Components/MainMenu";
import { useHref } from 'react-router-dom';

import { DatePicker } from "antd";
import moment from "moment";
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
  const [reccuring, setReccuring] = useState(false);

  //Validation
  const [nameDirty, setNameDirty] = useState(false);
  const [startDateDirty, setStartDateDirty] = useState(false);
  const [endDateDirty, setEndDateDirty] = useState(false);
  
  const [nameError, setNameError] = useState("Negali būti tuščias!")
  const [startDateError, setStartDateError] = useState("Negali būti tuščias!")
  const [endDateError, setEndDateError] = useState("Negali būti tuščias!")

  const [formValid, setFormValid] = useState(false)


  useEffect(() => {
    if (nameError || startDateError|| endDateError) {
      setFormValid(false)
    } else {
      setFormValid(true)
    }
  }, [nameError,startDateError,endDateError])

  const blurHandler = (e) => {
    switch (e.target.name) {
      case "name":
        setNameDirty(true);
        break;
        case "startDate":
          setStartDateDirty(true);
        break;
        case "endDate":
          setEndDateDirty(true);
        break;
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

  const startDateHandler = (date) => {
    if(date){
      const newDate = dayjs(date).format("YYYY-MM-DD");
      setStartDate(newDate);
      setStartDateError("")
    } else {
      setStartDateError("Negali būti tuščias!")
    }
  }

  const endDateHandler = (date) => {
    if(date){
      if(!date.isBefore(startDate)){
        const newDate = dayjs(date).format("YYYY-MM-DD");
      setEndDate(newDate);
      setEndDateError("")
      } else {
        setEndDateError("Data iki negali buti anksciau nei Data nuo")
      }
    } else {
      setEndDateError("Negali būti tuščias!")
    }       
  }
  
  const handleReccuringChange = (event) => {
    setReccuring(event.target.value === 'yes');
  }
  
  const disabledStartDate = (current) => {
    return current && current < moment().startOf("year");
  };

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
        reccuring,
      })
    }).then(applyResult);
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
              <label>Atostogų pavadinimas</label>
              {(nameDirty && nameError) && <div style={{ color: "red" }}>{nameError}</div>}
              <input 
              name="name"  
              onBlur={blurHandler} 
              placeholder='Atostogų pavadinimas' 
              value={name} 
              onChange={e => nameHandler(e)} 
              />
            </Form.Field>
            <Form.Field >
              <label>Data nuo</label>
            </Form.Field>
          </Form>

              {(startDateDirty && startDateError) && <div style={{ color: "red" }}>{startDateError}</div>}
              <DatePicker 
              name="startDate"
               onBlur={blurHandler}
               className="controls4"
               placeholder="Data nuo"
               onChange={startDateHandler}
               disabledDate={disabledStartDate}
            />
            
            <Form style={{ paddingTop: '10px' }}>
            <Form.Field >
              <label>Data iki</label>
             </Form.Field> 
            </Form>

            {(endDateDirty && endDateError) && <div style={{ color: "red" }}>{endDateError}</div>}
              <DatePicker
              name="endDate"
              onBlur={blurHandler}
               className="controls4"
               placeholder="Data iki"
               onChange={endDateHandler}
               disabledDate={disabledStartDate}
              />

            <Form style={{ paddingTop: '10px' }}>
              <Form.Field>
                <label>Ar atostogos pasikartoja?</label>
              </Form.Field>
              <Form.Group >
                <Form.Field><label>Taip</label></Form.Field>
                <input type="radio"
                value="yes" 
                
                style={{ marginRight: '10px', marginBottom: '14px' }}
                onChange={handleReccuringChange}/>
                <Form.Group >
                <Form.Field ><label>Ne</label></Form.Field>
                <input type="radio"
                value="no"
            
                onChange={handleReccuringChange} />
              </Form.Group>
              </Form.Group>
            </Form>
            
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