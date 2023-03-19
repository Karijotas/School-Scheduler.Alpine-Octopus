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

const JSON_HEADERS = {
  "Content-Type": "application/json",
};

export function EditGroupObject() {
  const params = useParams();

  const [active, setActive] = useState(true);

  const [programs, setPrograms] = useState([]);

  const [shifts, setShifts] = useState([]);

  const [error, setError] = useState();

  const [groups, setGroups] = useState({
    name: "",
    studentAmount: "",
    schoolYear: "",
    program: "",
    shift: "",
    modifiedDate: "",
  });

  const [programId, setProgramId] = useState();

  const [shiftId, setShiftId] = useState();

  const [name, setName] = useState("");
  const [studentAmount, setstudentAmount] = useState("");

  const [nameError, setNameError] = useState("");
  const [studentAmountError, setstudentAmountError] = useState("");

  const [selectErrorYear, setSelectErrorYear] = useState("");
  const [selectErrorProgram, setSelectErrorProgram] = useState("*Privaloma");
  const [selectErrorShift, setSelectErrorShift] = useState("*Privaloma");

  const [formValid, setFormValid] = useState(false);

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

  const handleStudentAmountInputChange = (e) => {
    groups.studentAmount = e.target.value;
    setstudentAmount(e.target.value);
    validateStudentAmountInput(e.target.value);
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

  const validateStudentAmountInput = (value) => {
    if (!/^\d+$/.test(value)) {
      setstudentAmountError("Įveskite tik skaičius");
      if (!value) {
        setstudentAmountError("Negali būti tuščias");
      }
    } else if (value > 300 || value < 1) {
      setstudentAmountError("Skaičius turi būti tarp 1 ir 300");
    } else {
      setstudentAmountError("");
    }
  };

  useEffect(() => {
    fetch("/api/v1/groups/" + params.id)
      .then((response) => response.json())
      .then(setGroups);
  }, [active, params]);

  const applyResult = () => {
    setActive(true);
  };

  const updateGroups = () => {
    fetch(
      "/api/v1/groups/" +
        params.id +
        "?programId=" +
        programId +
        "&shiftId=" +
        shiftId,
      {
        method: "PATCH",
        headers: JSON_HEADERS,
        body: JSON.stringify(groups),
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
    setGroups({
      ...groups,
      [property]: event.target.value,
    });
  };

  const editThis = () => {
    setActive(false);
    setProgramId(groups.programId);
    setShiftId(groups.shiftId);
  };

  useEffect(() => {
    fetch("/api/v1/programs")
      .then((response) => response.json())
      .then((data) =>
        setPrograms(
          data.map((x) => {
            return { key: x.id, text: x.name, value: x.id };
          })
        )
      );
  }, []);

  useEffect(() => {
    fetch("/api/v1/shifts")
      .then((response) => response.json())
      .then((data) =>
        setShifts(
          data.map((x) => {
            return { key: x.id, text: x.name, value: x.id };
          })
        )
      );
  }, []);

  const [updated, setUpdated] = useState();

  useEffect(() => {
    setUpdated(true);
  }, [setUpdated]);

  return (
    <div>
      <MainMenu />
      <Grid columns={2}>
        <Grid.Column width={2} id="main">
          <EditMenu active="groups" />
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
                        Grupės pavadinimas "Teams"
                      </Table.HeaderCell>
                      <Table.HeaderCell>Mokslo metai</Table.HeaderCell>
                      <Table.HeaderCell>
                        Paskutinis atnaujinimas
                      </Table.HeaderCell>
                      <Table.HeaderCell>Veiksmai</Table.HeaderCell>
                    </Table.Row>
                  </Table.Header>
                  <Table.Body>
                    <Table.Row>
                      <Table.Cell width={6}>{groups.name}</Table.Cell>
                      <Table.Cell>{groups.schoolYear}</Table.Cell>
                      <Table.Cell width={3}> {groups.modifiedDate} </Table.Cell>
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
                <Table celled>
                  <Table.Header>
                    <Table.Row>
                      <Table.HeaderCell>Studentų skaičius</Table.HeaderCell>
                      <Table.HeaderCell>Programa</Table.HeaderCell>
                      <Table.HeaderCell>Pamaina</Table.HeaderCell>
                    </Table.Row>
                  </Table.Header>

                  <Table.Body>
                    <Table.Row>
                      <Table.Cell width={6}>{groups.studentAmount}</Table.Cell>
                      <Table.Cell>{groups.programName} </Table.Cell>
                      <Table.Cell width={4}>{groups.shiftName} </Table.Cell>
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
                        Grupės pavadinimas "Teams"
                      </Table.HeaderCell>
                      <Table.HeaderCell>Mokslo metai</Table.HeaderCell>
                      <Table.HeaderCell>
                        Paskutinis atnaujinimas
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
                          value={groups.name}
                          onChange={(e) => handleNameInputChange(e)}
                        />
                      </Table.Cell>
                      <Table.Cell>
                        <select
                          value={groups.schoolYear}
                          onChange={(e) => updateProperty("schoolYear", e)}
                        >
                          {Object.entries(YEAR_OPTIONS).map(([key, value]) => (
                            <option value={key}>{value}</option>
                          ))}
                        </select>
                      </Table.Cell>
                      <Table.Cell width={4}> {groups.modifiedDate} </Table.Cell>
                    </Table.Row>
                  </Table.Body>
                </Table>
                <Divider horizontal hidden></Divider>
                <Table celled>
                  <Table.Header>
                    <Table.Row>
                      <Table.HeaderCell>Studentų skaičius</Table.HeaderCell>
                      <Table.HeaderCell>Programa</Table.HeaderCell>
                      <Table.HeaderCell>Pamaina</Table.HeaderCell>
                    </Table.Row>
                  </Table.Header>

                  <Table.Body>
                    <Table.Row>
                      <Table.Cell width={6}>
                        {studentAmountError && (
                          <div style={{ color: "red" }}>
                            {studentAmountError}
                          </div>
                        )}
                        <Input
                          value={groups.studentAmount}
                          onChange={(e) => handleStudentAmountInputChange(e)}
                        />
                      </Table.Cell>
                      <Table.Cell collapsing>
                        {/* {(selectErrorProgram) && <div style={{color: "red"}}>{selectErrorProgram}</div>} */}
                        <Select
                          options={programs}
                          placeholder={groups.programName}
                          onChange={(e, data) => setProgramId(data.value)}
                        />
                      </Table.Cell>
                      <Table.Cell width={4}>
                        <Select
                          options={shifts}
                          placeholder={groups.shiftName}
                          onChange={(e, data) => setShiftId(data.value)}
                        />
                      </Table.Cell>
                    </Table.Row>
                  </Table.Body>
                </Table>

                <Button onClick={() => setActive(true)}>Atšaukti</Button>
                <Button
                  disabled={!formValid}
                  className="controls"
                  id="details"
                  onClick={updateGroups}
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
