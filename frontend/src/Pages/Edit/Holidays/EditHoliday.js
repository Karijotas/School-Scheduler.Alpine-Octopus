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
  Table,
  Form,
} from "semantic-ui-react";
import { EditMenu } from "../../../Components/EditMenu";
import MainMenu from "../../../Components/MainMenu";
import { DatePicker } from "antd";
import dayjs from "dayjs";
import moment from "moment";

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
    reccuring: "",
  });
  const [name, setName] = useState("");
  const [startDate, setStartDate] = useState();
  const [endDate, setEndDate] = useState();
  const [reccuring, setReccuring] = useState(holidays.reccuring);

  //Validation
 
  const [nameError, setNameError] = useState("")
  const [startDateError, setStartDateError] = useState("")
  const [endDateError, setEndDateError] = useState("")

  const [formValid, setFormValid] = useState(false);

  useEffect(() => {
    if (nameError || startDateError|| endDateError) {
      setFormValid(false)
    } else {
      setFormValid(true)
    }
  }, [nameError,startDateError,endDateError])

  useEffect(() => {
    setReccuring(holidays.reccuring);
  }, [holidays.reccuring]);

  const handleNameInputChange = (e) => {
    holidays.name = e.target.value;
    setName(e.target.value);
    validateNameInput(e.target.value);
  };

  const handleStartDateInputChange = (e) => {
    holidays.startDate = e.target.value;
    const newDate = dayjs(e).format("YYYY-MM-DD");
      setStartDate(newDate);
    validateStartDate(e.target.value)
  }

  const handleEndDateInputChange = (e) => {
    holidays.startDate = e.target.value;
    const newDate = dayjs(e).format("YYYY-MM-DD");
      setEndDate(newDate);
    validateEndDate(e.target.value)
  }

  const handleReccuringChange = (e) => {
    if (e.target.value === 'yes') {
      holidays.reccuring = true
      setReccuring(true)
    } else {
      holidays.reccuring = false
      setReccuring(false)
    }
  }

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
  const validateStartDate = (value) => {
    if(value){
      setStartDateError("")
    } else {
      setStartDateError("Negali būti tuščias!")
    }
  }

  const validateEndDate = (value) => {
    if(!value){
      setEndDateError("Negali būti tuščias!")
    } else {
      if(value.isBefore(startDate)){
        setEndDateError("Data iki negali buti anksciau nei Data nuo")
      }
      setEndDateError("")
    }       
  }

  useEffect(() => {
    setStartDate(holidays.startDate)
    setEndDate(holidays.endDate)
  }, [holidays])

  useEffect(() => {
    fetch("/api/v1/holidays/" + params.id)
      .then((response) => response.json())
      .then(setHolidays);
  }, [active, params]);

  const applyResult = () => {
    setActive(true);
  };
  
  const disabledStartDate = (current) => {
    return current && current < moment().startOf("year");
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
          setError("Update failed. Check if name is unique and dates doesn't overlap with other holidays!");
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
                      <Table.HeaderCell>Atostogų pavadinimas</Table.HeaderCell>
                      <Table.HeaderCell>Data nuo</Table.HeaderCell>
                      <Table.HeaderCell>Data iki</Table.HeaderCell>
                      <Table.HeaderCell>Pasikartoja</Table.HeaderCell>
                      <Table.HeaderCell>Veiksmai</Table.HeaderCell>
                    </Table.Row>
                  </Table.Header>
                  <Table.Body>
                    <Table.Row>
                      <Table.Cell width={6}>{holidays.name}</Table.Cell>
                      <Table.Cell>{holidays.startDate}</Table.Cell>
                      <Table.Cell width={3}> {holidays.endDate} </Table.Cell>
                      <Table.Cell width={3}> {holidays.reccuring ? "Taip" : "Ne"} </Table.Cell>
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
                        Atostogų pavadinimas
                      </Table.HeaderCell>
                      <Table.HeaderCell>
                        Data nuo
                      </Table.HeaderCell>
                      <Table.HeaderCell>
                        Data iki
                      </Table.HeaderCell>
                      <Table.HeaderCell>
                        Pasikartoja
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
                      {(startDateError) && <div style={{ color: "red" }}>{startDateError}</div>}
                        <DatePicker
                          className="controls4"
                          placeholder="Data nuo"
                          disabledDate={disabledStartDate}
                          defaultValue={dayjs(startDate)}
                          onChange={(e) => {
                            const newDate = dayjs(e);
                            if (!newDate.isValid()) {
                              setStartDateError("Negali buti tuscias")
                            } else {
                              setStartDateError("");
                              const formattedDate = newDate.format("YYYY-MM-DD");
                              setStartDate(formattedDate);
                              holidays.startDate = formattedDate;
                            }
                          }}
                        />
                      </Table.Cell>
                      <Table.Cell>
                        <DatePicker
                          className="controls4"
                          placeholder="Data iki"
                          disabledDate={disabledStartDate}
                          defaultValue={dayjs(endDate)}
                          onChange={(e) => {
                            const newDate = dayjs(e);
                            if (newDate.isValid()) {
                              const formattedDate = newDate.format("YYYY-MM-DD");
                              setEndDate(formattedDate);
                              holidays.endDate = formattedDate;
                            }
                          }}
                        />
                      </Table.Cell>
                      <Table.Cell>
                        <Form style={{ paddingTop: '10px' }}>
                          <Form.Group >
                            <Form.Field><label>Taip</label></Form.Field>
                            <input type="radio"
                              value="yes"

                              checked={holidays.reccuring}
                              style={{ marginRight: '10px', marginBottom: '14px' }}
                              onClick={handleReccuringChange}
                            // onChange={handleReccuringChange}
                            />
                            <Form.Group >
                              <Form.Field ><label>Ne</label></Form.Field>
                              <input type="radio"
                                value="no"
                                defaultChecked={!holidays.reccuring}
                                checked={!holidays.reccuring}
                                onClick={handleReccuringChange}
                              // onChange={handleReccuringChange} 
                              />
                            </Form.Group>
                          </Form.Group>
                        </Form>

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
