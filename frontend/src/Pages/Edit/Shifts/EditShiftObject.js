import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import {
  Button,
  Divider, Grid, Icon,
  Input, Segment, Table
} from "semantic-ui-react";
import { EditMenu } from '../../../Components/EditMenu';
import MainMenu from "../../../Components/MainMenu";

const JSON_HEADERS = {
  "Content-Type": "application/json",
};

export function EditShiftObject() {
  const params = useParams();


  const [hide, setHide] = useState(false);
  const [active, setActive] = useState(true);
  const [error, setError] = useState();
  const [shifts, setShifts] = useState({
    name: "",
    starts: "",
    ends: "",
  });

  const [name, setName] = useState("");
  const [starts, setStarts] = useState("");
  const [ends, setEnds] = useState("");

  const [nameError, setNameError] = useState("")
  const [startError, setStartError] = useState("")
  const [endError, setEndError] = useState("")

  const [formValid, setFormValid] = useState(false)


  useEffect(() => {
    if (nameError || startError || endError) {
      setFormValid(false)
    } else {
      setFormValid(true)
    }
  }, [nameError, startError, endError])

  const handleNameInputChange = (e) => {
    shifts.name = e.target.value
    setName(e.target.value);
    validateNameInput(e.target.value);
  };

  const handleStartInputChange = (e) => {
    shifts.starts = e.target.value
    setStarts(e.target.value);
    validateStartInput(e.target.value);
  };

  const handleEndInputChange = (e) => {
    shifts.ends = e.target.value
    setEnds(e.target.value);
    validateEndInput(e.target.value);
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

  const validateStartInput = (value) => {

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

  const validateEndInput = (value) => {

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

  useEffect(() => {
    fetch("/scheduler/api/v1/shifts/" + params.id)
      .then((response) => response.json())
      .then(setShifts)
      .then(setStarts(shifts.starts))
      .then(setEnds(shifts.ends));
  }, [active, params]);

  const applyResult = () => {
    setActive(true);
  };

  const updateShifts = () => {
    fetch("/scheduler/api/v1/shifts/" + params.id, {
      method: "PUT",
      headers: JSON_HEADERS,
      body: JSON.stringify(shifts),
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
    setShifts({
      ...shifts,
      [property]: event.target.value,
    });
  };

  const editThis = () => {
    setActive(false);
  };



  return (
    <div>
      <MainMenu />
      <Grid columns={2}>
        <Grid.Column width={2} id="main">
          <EditMenu active="shifts" />
        </Grid.Column>
        <Grid.Column
          floated="left"
          textAlign="left"
          verticalAlign="top"
          width={13}
        >
          <Segment raised color="teal">
            {active && (
              <div>
                <Table celled>
                  <Table.Header>
                    <Table.Row>
                      <Table.HeaderCell>Pavadinimas</Table.HeaderCell>
                      <Table.HeaderCell>Pamokos nuo</Table.HeaderCell>
                      <Table.HeaderCell>Pamokos iki</Table.HeaderCell>
                      <Table.HeaderCell width={3}>Paskutinis atnaujinimas</Table.HeaderCell>
                      <Table.HeaderCell>Veiksmai</Table.HeaderCell>
                    </Table.Row>
                  </Table.Header>

                  <Table.Body>
                    <Table.Row>
                      <Table.Cell>{shifts.name}</Table.Cell>
                      <Table.Cell>{shifts.starts}</Table.Cell>
                      <Table.Cell>{shifts.ends}</Table.Cell>
                      <Table.Cell collapsing>
                        {" "}
                        {shifts.modifiedDate}{" "}
                      </Table.Cell>
                      <Table.Cell collapsing>
                        <Button onClick={editThis} id='details' >Redaguoti</Button>
                      </Table.Cell>
                    </Table.Row>
                  </Table.Body>
                </Table>


                <Divider hidden />
                <Button
                  icon
                  labelPosition="left"
                  className=""
                  href="#/view/shifts"
                >
                  <Icon name="arrow left" />
                  Atgal
                </Button>
              </div>
            )}
            {!active && (
              <div>
                <Table celled>
                  <Table.Header>
                    <Table.Row>
                      <Table.HeaderCell>Pavadinimas</Table.HeaderCell>
                      <Table.HeaderCell>Pamokos nuo</Table.HeaderCell>
                      <Table.HeaderCell>Pamokos iki</Table.HeaderCell>
                      <Table.HeaderCell>Paskutinis atnaujinimas</Table.HeaderCell>
                    </Table.Row>
                  </Table.Header>

                  <Table.Body>
                    <Table.Row>
                      <Table.Cell collapsing>
                        {(nameError) && <div style={{ color: "red" }}>{nameError}</div>}
                        <Input
                          value={shifts.name}
                          onChange={(e) => handleNameInputChange(e)}
                        />
                      </Table.Cell>
                      <Table.Cell collapsing>
                        {(startError) && <div style={{ color: "red" }}>{startError}</div>}
                        <Input
                          value={shifts.starts}
                          onChange={(e) => handleStartInputChange(e)}
                        />
                      </Table.Cell>
                      <Table.Cell collapsing>
                        {(endError) && <div style={{ color: "red" }}>{endError}</div>}
                        <Input
                          value={shifts.ends}
                          onChange={(e) => handleEndInputChange(e)}
                        />
                      </Table.Cell>
                      <Table.Cell collapsing>
                        {" "}
                        {shifts.modifiedDate}{" "}
                      </Table.Cell>
                    </Table.Row>
                  </Table.Body>
                </Table>
                <Divider hidden></Divider>
                <Button onClick={() => setActive(true)}>Atšaukti</Button>
                <Button id='details' disabled={!formValid} floated="right" primary onClick={updateShifts}>
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
