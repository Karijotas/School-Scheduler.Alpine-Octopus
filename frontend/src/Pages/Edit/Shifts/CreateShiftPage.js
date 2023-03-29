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
    if (nameError || startError || endError) {
      setFormValid(false)
    } else {
      setFormValid(true)
    }
  }, [nameError, endError, startError])

  const blurHandler = (e) => {
    switch (e.target.name) {
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
    if (e.target.value.length < 2 || e.target.value.length > 100) {
      setNameError("Įveskite nuo 2 iki 100 simbolių!")
      if (!e.target.value) {
        setNameError("Negali būti tuščias!")
      }
    } else {
      setNameError("")
    }
  }

  const startHandler = (e) => {
    const value = e.target.value;
    setStarts(value);

    if (!/^\d+$/.test(value)) {
      setStartError("Įveskite tik skaičius!");

      if (!value) {
        setStartError("Negali būti tuščias!");
      }
    } else if (value < 1 || value > 14) {
      setStartError("Skaičius turi būti tarp 1 ir 14!");
    } else if (Number(ends) && Math.abs(value - Number(ends)) > 11) {
      setStartError("Intervalo skirtumas negali būti didesnis nei 12!");
    } else if (Number(ends) && value > Number(ends)) {
      setStartError("Pamokos negali baigtis vėliau nei prasidėjo")
      setEndError("");
    } else {
      setStartError("");
    }
  };

  const endHandler = (e) => {
    const value = e.target.value;
    setEnds(value);


    if (!/^\d+$/.test(value)) {
      setEndError("Įveskite tik skaičius!");

      if (!value) {
        setEndError("Negali būti tuščias!");
      }
    } else if (value < 1 || value > 14) {
      setEndError("Skaičius turi būti tarp 1 ir 14!");
    } else if (Number(starts) && Math.abs(Number(starts) - value) > 11) {
      setEndError("Intervalo skirtumas negali būti didesnis nei 12!");
    } else if (Number(starts) > value) {
      setEndError("Pamokos negali baigtis anksčiau nei prasidėjo!")
      setStartError("");
    } else {
      setEndError("");
    }
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

  const createShift = () => {
    fetch("/alpine/api/v1/shifts", {
      method: "POST",
      headers: JSON_HEADERS,
      body: JSON.stringify({
        name,
        starts,
        ends,
      }),
    }).then(applyResult);
  };

  const fetchShifts = async () => {
    fetch(`/alpine/api/v1/shifts/`)
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
                  {(nameDirty && nameError) && <div style={{ color: "red" }}>{nameError}</div>}
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
                  {(startDirty && startError) && <div style={{ color: "red" }}>{startError}</div>}
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
                  {(endDirty && endError) && <div style={{ color: "red" }}>{endError}</div>}
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
