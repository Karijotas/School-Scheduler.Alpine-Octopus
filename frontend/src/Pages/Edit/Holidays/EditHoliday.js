import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import {
  Button,
  Divider,
  Grid,
  Icon,
  Input,
  Message,
  Segment,
  Select,
  Table,
} from "semantic-ui-react";
import { YEAR_OPTIONS } from "../../../Components/const";
import { EditMenu } from "../../../Components/EditMenu";
import MainMenu from "../../../Components/MainMenu";
import { DatePicker } from "antd";
import dayjs from "dayjs";

const JSON_HEADERS = {
  "Content-Type": "application/json",
};

export function EditHoliday() {
  const params = useParams();

  const [active, setActive] = useState(true);

  const [error, setError] = useState();

  const [holidays, setHolidays] = useState({
    name: "",
    startDate: "",
    endDate: "",
  });
const [name, setName] = useState("");
  const [startDate, setStartDate] = useState('');
  const [endDate, setEndDate] = useState('');

  
  const [nameError, setNameError] = useState("");
//   const [starDateError, setStarDateError] = useState("");
//   const [endDateError, setEndDateError] = useState("");

  const [formValid, setFormValid] = useState(false);

  useEffect(() => {
    if (nameError) {
      setFormValid(false);
    } else {
      setFormValid(true);
    }
  }, [nameError]);

  //   const selectProgramHandler = () => {
  //     setSelectErrorProgram("")
  //   }
  //   const selectShiftHandler = () => {
  //     setSelectErrorShift("")
  //   }

  const handleNameInputChange = (e) => {
    holidays.name = e.target.value;
    setName(e.target.value);
    validateNameInput(e.target.value);
  };

//   const handleStartDateInputChange = (e) => {
//     holidays.starDate = e.target.value;
//     setStartDate(e.target.value);
//   };

//   const handleEndDateInputChange = (e) => {
//     holidays.endDate = e.target.value;
//     setEndDate(e.target.value);
//   };

  const validateNameInput = (value) => {
    if (value.length < 2 || value.length > 100) {
      setNameError("Įveskite nuo 2 iki 100 simbolių!");
      if (!value) {
        setNameError("Pavadinimas negali būti tuščias!");
      }
    } else {
      setNameError("");
    }
  };

//   const validateStartDateInput = (value) => {
//     
//   };

  useEffect(() => {
    fetch("/api/v1/holidays/" + params.id)
      .then((response) => response.json())
      .then(setHolidays);
  }, [active, params]);

  const applyResult = () => {
    setActive(true);
  };

  const updateHolidays = () => {
    fetch(
      "/api/v1/holidays/" +
        params.id,
      {
        method: "PUT",
        headers: JSON_HEADERS,
        body: JSON.stringify(holidays),
      }
    )
      .then((result) => {
        if (!result.ok) {
          setError("Update failed. Please fill all fields");
        } else {
          setError();
        }
      })
      .then(applyResult);
  };

  const updateProperty = (property, event) => {
    setHolidays({
      ...holidays,
      [property]: event.target.value,
    });
  };

  const editThis = () => {
    setActive(false);
  };

  const [updated, setUpdated] = useState();

  useEffect(() => {
    setUpdated(true);
  }, [setUpdated]);

  return (
    <div>
      <MainMenu />
      <Grid columns={2}>
        <Grid.Column width={2} id="main"> 
          <EditMenu active="holidays" />
        </Grid.Column>
        <Grid.Column
          floated="left"
          textAlign="left"
          verticalAlign="top"
          width={13}
        >
          <Segment id="segment" color="teal">
            {active && (
              <div>
                {" "}
                {error && (
                  <Message warning className="error">
                    {error}
                  </Message>
                )}
                <Table celled>
                  <Table.Header>
                    <Table.Row>
                      <Table.HeaderCell>Atostogų Pavadinimas</Table.HeaderCell>
                      <Table.HeaderCell>Data nuo</Table.HeaderCell>
                      <Table.HeaderCell>Data iki</Table.HeaderCell>
                      <Table.HeaderCell>Veiksmai</Table.HeaderCell>
                    </Table.Row>
                  </Table.Header>
                  <Table.Body>
                    <Table.Row>
                      <Table.Cell width={6}>{holidays.name}</Table.Cell>
                      <Table.Cell>{holidays.startDate}</Table.Cell>
                      <Table.Cell width={3}> {holidays.endDate} </Table.Cell>
                      <Table.Cell collapsing>
                        {" "}
                        <Button
                          id="details"
                          className="controls"
                          onClick={editThis}
                        >
                          Redaguoti
                        </Button>{" "}
                      </Table.Cell>
                    </Table.Row>
                  </Table.Body>
                </Table>
                <Divider horizontal hidden></Divider>
                <Button icon labelPosition="left" href="#/view/holidays">
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
                      <Table.HeaderCell>
                      Atostogų Pavadinimas
                      </Table.HeaderCell>
                      <Table.HeaderCell>Data nuo</Table.HeaderCell>
                      <Table.HeaderCell>
                        Data iki
                      </Table.HeaderCell>
                    </Table.Row>
                  </Table.Header>
                  <Table.Body>
                    <Table.Row>
                      <Table.Cell width={6}>
                        {nameError && (
                          <div style={{ color: "red" }}>{nameError}</div>
                        )}
                        <Input
                          value={holidays.name}
                          onChange={(e) => handleNameInputChange(e)}
                        />
                      </Table.Cell>
                      <Table.Cell>
                      <DatePicker
              className="controls4"
              placeholder="Data nuo"
              value={holidays.starDate}
              onChange={(e) => {const newDate = dayjs(e).format("YYYY-MM-DD");setStartDate(newDate);holidays.startDate = newDate}}
            />
                      </Table.Cell>
                      <Table.Cell>
                      <DatePicker
              className="controls4"
              placeholder="Data iki"
              value={holidays.starDate}
              onChange={(e) => {
                const newDate = dayjs(e).format("YYYY-MM-DD");
                setEndDate(newDate);holidays.endDate = newDate
              }}
            />
                      </Table.Cell>
                      
                    </Table.Row>
                  </Table.Body>
                </Table>
                <Divider horizontal hidden></Divider>

                <Button onClick={() => setActive(true)}>Atšaukti</Button>
                <Button
                  disabled={!formValid}
                  className="controls"
                  id="details"
                  onClick={updateHolidays}
                >
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
