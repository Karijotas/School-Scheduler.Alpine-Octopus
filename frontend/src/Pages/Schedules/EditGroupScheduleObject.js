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
  TextArea,
  Form,
} from "semantic-ui-react";
import MainMenu from "../../Components/MainMenu";
import { SchedulesMenu } from "../../Components/SchedulesMenu";
import { ScheduleView } from "./Schedule";

const JSON_HEADERS = {
  "Content-Type": "application/json",
};

export function EditGroupScheduleObject() {
  const params = useParams();

  const [active, setActive] = useState(true);
  const [startingDate, setStartingDate] = useState("");
  const [plannedTillDate, setPlannedTillDate] = useState("");

  const [programs, setPrograms] = useState([]);

  const [shifts, setShifts] = useState([]);

  const [error, setError] = useState();

  const [groupId, setGroupId] = useState("");
  const [groups, setGroups] = useState({
    name: "",
    studentAmount: "",
    schoolYear: "",
    program: "",
    shift: "",
    modifiedDate: "",
  });
  const [schedules, setSchedules] = useState({
    name: "",
    startingDate: "",
    plannedTillDate: "",
    status: "",
    lessons: "",
    group: "",
    shift: "",
    groupName: "",
    shiftName: "",
  });

  const [programId, setProgramId] = useState();

  const [shiftId, setShiftId] = useState();

  const [name, setName] = useState("");
  const [studentAmount, setstudentAmount] = useState("");

  const [nameError, setNameError] = useState("");
  const [studentAmountError, setstudentAmountError] = useState("");

  const [formValid, setFormValid] = useState(false);
  const [startingDateError, setStartingDateError] = useState("");
  const [plannedTillDateError, setPlannedTillDateError] = useState("");

  useEffect(() => {
    if (nameError || studentAmountError) {
      setFormValid(false);
    } else {
      setFormValid(true);
    }
  }, [nameError, studentAmountError]);

  //   const selectProgramHandler = () => {
  //     setSelectErrorProgram("")
  //   }
  //   const selectShiftHandler = () => {
  //     setSelectErrorShift("")
  //   }

  const handleNameInputChange = (e) => {
    groups.name = e.target.value;
    setName(e.target.value);
    validateNameInput(e.target.value);
  };

  const handlePlannedTillDateInputChange = (e) => {
    schedules.plannedTillDate = e.target.value;
    setPlannedTillDate(e.target.value);
    validatePlannedTillDateInput(e.target.value);
  };

  const handleStartingDateInputChange = (e) => {
    schedules.startingDate = e.target.value;
    setStartingDate(e.target.value);
    validateStartingDateInput(e.target.value);
  };

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

  const validatePlannedTillDateInput = (value) => {
    if (!/^\d+$/.test(value)) {
      setPlannedTillDateError("Įveskite datą");
      if (!value) {
        setPlannedTillDateError("Negali būti tuščias");
      }
    } else if (value > 20 || value < 1) {
      setPlannedTillDateError("Skaičius turi būti tarp 1 ir 20");
    } else {
      setPlannedTillDateError("");
    }
  };

  const validateStartingDateInput = (value) => {
    if (!/^\d+$/.test(value)) {
      setStartingDateError("Įveskite datą");
      if (!value) {
        setStartingDateError("Negali būti tuščias");
      }
    } else if (value > 20 || value < 1) {
      setStartingDateError("Skaičius turi būti tarp 1 ir 20");
    } else {
      setStartingDateError("");
    }
  };

  useEffect(() => {
    fetch("/api/v1/groups/" + schedules.groupName)
      .then((response) => response.json())
      .then(setGroups);
  }, [schedules]);

  useEffect(() => {
    fetch("/api/v1/schedule/" + params.id)
      .then((response) => response.json())
      .then(setSchedules);
  }, [active, params]);

  const applyResult = () => {
    setActive(true);
  };

  const updateSchedules = () => {
    fetch(
      "/api/v1/schedule/" +
      params.id +
      "?groupId=" +
      groupId +
      "&shiftId=" +
      shiftId,
      {
        method: "PATCH",
        headers: JSON_HEADERS,
        body: JSON.stringify(schedules),
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
    setSchedules({
      ...schedules,
      [property]: event.target.value,
    });
  };

  const editThis = () => {
    setActive(false);
    // setProgramId(groups.programId);
    // setShiftId(groups.shiftId)
  };

  // useEffect(() => {
  //     fetch('/api/v1/programs/')
  //         .then((response) => response.json())
  //         .then((data) =>
  //             setPrograms(
  //                 data.map((x) => {
  //                     return { key: x.id, text: x.name, value: x.id };
  //                 })
  //             )
  //         )
  // }, []);

  // useEffect(() => {
  //     fetch('/api/v1/shifts')
  //         .then((response) => response.json())
  //         .then((data) => setShifts(
  //             data.map((x) => {
  //                 return { key: x.id, text: x.name, value: x.id };
  //             })
  //         )
  //         )
  // }, []);

  const [updated, setUpdated] = useState();

  useEffect(() => {
    setUpdated(true);
  }, [setUpdated]);

  return (
    <div>
      <MainMenu />
      <Grid columns={2}>
        <Grid.Column width={2} id="main">
          <SchedulesMenu />
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
                      <Table.HeaderCell>
                        Tvarkaraščio pavadinimas
                      </Table.HeaderCell>
                      <Table.HeaderCell>Data nuo</Table.HeaderCell>
                      <Table.HeaderCell>Data iki</Table.HeaderCell>
                      <Table.HeaderCell>Veiksmai</Table.HeaderCell>
                    </Table.Row>
                  </Table.Header>
                  <Table.Body>
                    <Table.Row>
                      <Table.Cell width={6}>{schedules.name}</Table.Cell>
                      <Table.Cell>{schedules.startingDate}</Table.Cell>
                      <Table.Cell width={3}>
                        {" "}
                        {schedules.plannedTillDate}{" "}
                      </Table.Cell>
                      <Table.Cell width={1}>
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
                <Table celled>
                  <Table.Header>
                    <Table.Row>
                      <Table.HeaderCell>
                        Grupės pavadinimas "Teams"
                      </Table.HeaderCell>
                      <Table.HeaderCell>Mokslo metai</Table.HeaderCell>
                      <Table.HeaderCell width={4}>
                        Studentų skaičius
                      </Table.HeaderCell>
                    </Table.Row>
                  </Table.Header>
                  <Table.Body>
                    <Table.Row>
                      <Table.Cell width={6}>{groups.name}</Table.Cell>
                      <Table.Cell>{groups.schoolYear}</Table.Cell>
                      <Table.Cell>{groups.studentAmount}</Table.Cell>
                    </Table.Row>
                  </Table.Body>
                </Table>
                <Divider horizontal hidden></Divider>
                <Table celled>
                  <Table.Header>
                    <Table.Row>
                      <Table.HeaderCell>Programa</Table.HeaderCell>
                      <Table.HeaderCell width={4}>Pamaina</Table.HeaderCell>
                    </Table.Row>
                  </Table.Header>

                  <Table.Body>
                    <Table.Row>
                      <Table.Cell>{groups.programName} </Table.Cell>
                      <Table.Cell>{schedules.shiftName} </Table.Cell>
                    </Table.Row>
                  </Table.Body>
                </Table>
                <Button icon labelPosition="left" href="#/view/groups">
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
                        Tvarkaraščio pavadinimas
                      </Table.HeaderCell>
                      <Table.HeaderCell>Data nuo</Table.HeaderCell>
                      <Table.HeaderCell>Data iki</Table.HeaderCell>
                    </Table.Row>
                  </Table.Header>
                  <Table.Body>
                    <Table.Row>
                      <Table.Cell width={6}>
                        <Form>
                          <Input
                            fluid
                            value={schedules.name}
                            onChange={(e) => updateProperty("name", e)}
                          />
                        </Form>
                      </Table.Cell>
                      <Table.Cell>{schedules.startingDate}</Table.Cell>
                      <Table.Cell width={3}>
                        {" "}
                        {schedules.plannedTillDate}{" "}
                      </Table.Cell>
                      {/* <Table.Cell width={6}>
                        {nameError && (
                          <div style={{ color: "red" }}>{nameError}</div>
                        )}
                        <Input
                          value={schedules.name}
                          onChange={(e) => handleNameInputChange(e)}
                        />
                      </Table.Cell>
                      <Table.Cell collapsing>
                        {(startingDate) && <div style={{ color: "red" }}>{startingDateError}</div>}
                        <Input
                          value={schedules.startingDate}
                          onChange={(e) => handleStartingDateInputChange(e)}
                        />
                      </Table.Cell>
                      <Table.Cell collapsing>
                        {(plannedTillDate) && <div style={{ color: "red" }}>{plannedTillDate}</div>}
                        <Input
                          value={schedules.plannedTillDate}
                          onChange={(e) => handlePlannedTillDateInputChange(e)}
                        />
                      </Table.Cell> */}
                      {/* </Table.Row>
                  </Table.Body>
                </Table>
                <Divider horizontal hidden></Divider>
                <Table celled>
                  <Table.Header>
                    <Table.Row>
                      <Table.HeaderCell>
                        Grupės pavadinimas "Teams"
                      </Table.HeaderCell>
                      <Table.HeaderCell>Mokslo metai</Table.HeaderCell>
                      <Table.HeaderCell width={4}>
                        Studentų skaičius
                      </Table.HeaderCell>
                    </Table.Row>
                  </Table.Header>
                  <Table.Body>
                    <Table.Row>
                      <Table.Cell width={6}>{groups.name}</Table.Cell>
                      <Table.Cell>{groups.schoolYear}</Table.Cell>
                      <Table.Cell>{groups.studentAmount}</Table.Cell>
                    </Table.Row>
                  </Table.Body>
                </Table>
                <Divider horizontal hidden></Divider>
                <Table celled>
                  <Table.Header>
                    <Table.Row>
                      <Table.HeaderCell>Programa</Table.HeaderCell>
                      <Table.HeaderCell width={4}>Pamaina</Table.HeaderCell>
                    </Table.Row>
                  </Table.Header>

                  <Table.Body>
                    <Table.Row>
                      <Table.Cell>{groups.programName} </Table.Cell>
                      <Table.Cell>{groups.shiftName} </Table.Cell> */}
                    </Table.Row>
                  </Table.Body>
                </Table>
                <Button onClick={() => setActive(true)}>Atšaukti</Button>
                <Button
                  disabled={!formValid}
                  className="controls"
                  id="details"
                  onClick={updateSchedules}
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
