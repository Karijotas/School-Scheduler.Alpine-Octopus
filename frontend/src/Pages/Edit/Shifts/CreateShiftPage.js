import React, { useEffect, useState } from "react";
import { NavLink, useHref } from 'react-router-dom';
import { Button, Form, Grid, Icon, Input, Segment } from "semantic-ui-react";
import { EditMenu } from '../../../Components/EditMenu';
import MainMenu from '../../../Components/MainMenu';

const JSON_HEADERS = {
  "Content-Type": "application/json",
};

export function CreateShiftPage() {
  // const [create, setCreate] = useState()
  const listUrl = useHref('/view/shifts');
  const [hide, setHide] = useState(false);
  const [name, setName] = useState("");
  const [starts, setStarts] = useState("");
  const [ends, setEnds] = useState("");
  const [shifts, setShifts] = useState("");

  //Validation
  const [nameDirty, setNameDirty] = useState(false);
  const [startDirty, setStartDirty] = useState(false);
  const [endDirty, setEndDirty] = useState(false);
  
  const [nameError, setNameError] = useState("Negali būti tuščias!")
  const [startError, setStartError] = useState("Negali būti tuščias!")
  const [endError, setEndError] = useState("Negali būti tuščias!")

  const [formValid, setFormValid] = useState(false)

  useEffect(() => {
    if(nameError || startError || endError) {
      setFormValid(false)
    } else {
      setFormValid(true)
    }
  }, [nameError, endError, startError])

  const blurHandler = (e) => {
    switch (e.target.name){
      case 'name':
        setNameDirty(true);
        break
        case 'starts': 
        setStartDirty(true);
          break
          case 'ends': 
          setEndDirty(true);
          break
    }
  }

  const nameHandler = (e) => {
    setName(e.target.value)
    if(e.target.value.length <2 || e.target.value.length > 40){
      setNameError("Įveskite nuo 2 iki 40 simbolių!")
      if(!e.target.value){
        setNameError("Negali būti tuščias!")
      }
    } else {
      setNameError("")
    }
  }

  const startHandler = (e) => {
    setStarts(e.target.value)
    if(!/^\d+$/.test(e.target.value)){
      setStartError("Įveskite tik skaičius!")
      if(!e.target.value){
        setStartError("Negali būti tuščias!")
      }
    } else {
      setStartError("")
    }
  }

  const endHandler = (e) => {
    setEnds(e.target.value)
    if(!/^\d+$/.test(e.target.value)){
      setEndError("Įveskite tik skaičius!")
      if(!e.target.value){
        setEndError("Negali būti tuščias!")
      }
    } else {
      setEndError("")
    }
    }
     
  

  const applyResult = (result) => {
    const clear = () => {
      setHide(true);
    };

    if (result.ok) {
      clear();
    } else {
      window.alert("Nepavyko sukurt: " + result.status);
    }
  };

  const createShift = () => {
    fetch("/scheduler/api/v1/shifts", {
      method: "POST",
      headers: JSON_HEADERS,
      body: JSON.stringify({
        name,
        starts,
        ends,
      }),
    }).then(applyResult).then(() => window.location = listUrl);
  };

  const fetchShifts = async () => {
    fetch(`/scheduler/api/v1/shifts/`)
      .then((response) => response.json())
      .then((jsonRespones) => setShifts(jsonRespones));
  };

  return (
    <div>
      <MainMenu />
      <Grid columns={2} >
        <Grid.Column width={2} id='main'>
          <EditMenu />
        </Grid.Column>
        <Grid.Column floated='left' textAlign='left' verticalAlign='top' width={13}>
          <Segment id='segment' color='teal'>
            <div className="create-new-page">
              <Form>
                <Form.Field>
                  <label>Pamainos pavadinimas</label>
                  {(nameDirty && nameError) && <div style={{color: "red"}}>{nameError}</div>}
                  <input
                    placeholder="Pamainos pavadinimas"
                    name="name"
                    onBlur={blurHandler}
                    value={name}
                    onChange={(e) => nameHandler(e)}
                  />
                </Form.Field>

                <Form.Field>
                  <label>Pamokos nuo</label>
                  {(startDirty && startError) && <div style={{color: "red"}}>{startError}</div>}
                  <Input
                    placeholder="Pamokos nuo:"
                    name="starts"
                    onBlur={blurHandler}
                    value={starts}
                    onChange={(e) => startHandler(e)}
                  />
                </Form.Field>

                <Form.Field>
                  <label>Pamokos iki</label>
                  {(endDirty && endError) && <div style={{color: "red"}}>{endError}</div>}
                  <Input
                    placeholder="Pamokos iki:"
                    name="ends"
                    onBlur={blurHandler}
                    value={ends}
                    onChange={(e) => endHandler(e)}
                  />
                </Form.Field>

                <div>
                  <Button
                    icon
                    labelPosition="left"
                    className=""
                    as={NavLink} exact to='/view/shifts'
                  >
                    <Icon name="arrow left" />
                    Atgal
                  </Button>
                  <Button
                    type="submit"
                    className="controls"
                    disabled={!formValid}
                    primary
                    id="details"
                    onClick={createShift}
                  >
                    Sukurti
                  </Button>
                </div>
              </Form>
            </div>
          </Segment>
        </Grid.Column>
      </Grid>
    </div>
  );
}
