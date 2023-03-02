import React, { useEffect, useState } from "react";
import { NavLink, useHref } from 'react-router-dom';
import { Button, Form, Grid, Icon, Segment } from "semantic-ui-react";
import { EditMenu } from '../../../Components/EditMenu';
import MainMenu from '../../../Components/MainMenu';

const JSON_HEADERS = {
  "Content-Type": "application/json",
};

export function CreateTeacher() {
  // const [create, setCreate] = useState()
  const listUrl = useHref('/view/teachers');
  const [hide, setHide] = useState(false);
  const [name, setName] = useState("");
  const [loginEmail, setLoginEmail] = useState("");
  const [contactEmail, setContactEmail] = useState("");
  const [phone, setPhone] = useState("");
  const [workHoursPerWeek, setWorkHoursPerWeek] = useState("");
  const [subjectId, setSubjectId] = useState("");
  const [subjects, setSubjects] = useState([]);

  //Validation
  const [nameDirty, setNameDirty] = useState(false);
  const [loginEmailDirty, setLoginEmailDirty] = useState(false);

  const [nameError, setNameError] = useState("Negali būti tuščias!")
  const [loginEmailError, setloginEmailError] = useState("Negali būti tuščias!")
  const [contactEmailError, setContactEmailError] = useState("")
  const [phoneError, setPhoneError] = useState("")
  const [workHoursError, setWorkHoursError] = useState("")

  const [formValid, setFormValid] = useState(false)

  useEffect(() => {
    if (nameError || loginEmailError || contactEmailError || workHoursError) {
      setFormValid(false)
    } else {
      setFormValid(true)
    }
  }, [nameError, loginEmailError, contactEmailError, workHoursError])

  const blurHandler = (e) => {
    switch (e.target.name) {
      case 'name':
        setNameDirty(true);
        break
      case 'teams':
        setLoginEmailDirty(true);
        break
    }
  }

  const nameHandler = (e) => {
    setName(e.target.value)
    if (!/^[\p{L}ĄČĘĖĮŠŲŪŽąčęėįšųūž\s-]+$/iu.test(e.target.value)) {
      setNameError("Įveskite tik raides")
      if (!e.target.value) {
        setNameError("Negali būti tuščias!")
      }
    } else if (e.target.value.length < 2 || e.target.value.length > 40) {
      setNameError("Įveskite nuo 2 iki 40 simbolių!")

    } else {
      setNameError("")
    }





  }

  const teamsLoginHandler = (e) => {
    setLoginEmail(e.target.value)
    if (!/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test(e.target.value)) {
      setloginEmailError("Neteisingas pašto formatas!")
      if (!e.target.value) {
        setloginEmailError("Negali būti tuščias!")
      }
    } else {
      setloginEmailError("")
    }
  }

  const emailHandler = (e) => {
    setContactEmail(e.target.value)
    if (!/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test(e.target.value)) {
      setContactEmailError("Neteisingas pašto formatas!")
    } else {
      setContactEmailError("")
    }
  }
  const phoneHandler = (e) => {
    setPhone(e.target.value)

    if (!/^(\+370|8)\d{8}$/.test(e.target.value)) {
      setPhoneError("Neteisingas numerio formatas!")
    } else {
      setPhoneError("")
    }
  }
  const hoursHandler = (e) => {
    setWorkHoursPerWeek(e.target.value)

    if (!/^\d+$/.test(e.target.value)) {
      setWorkHoursError("Įveskite tik skaičius")
      if (!e.target.value) {
        setWorkHoursError("")
      }
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

  const createTeacher = () => {
    fetch("/scheduler/api/v1/teachers?subjectId=" + subjectId, {
      method: "POST",
      headers: JSON_HEADERS,
      body: JSON.stringify({
        name,
        loginEmail,
        contactEmail,
        phone,
        workHoursPerWeek,

      }),
    }).then(applyResult).then(() => window.location = listUrl);
  };

  useEffect(() => {
    fetch("/scheduler/api/v1/subjects/")
      .then((response) => response.json())
      .then((data) =>
        setSubjects(
          data.map((x) => {
            return { key: x.id, text: x.name, value: x.id };
          })
        )
      );
  }, []);

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
                  <label>Vardas ir pavardė</label>
                  {(nameDirty && nameError) && <div style={{ color: "red" }}>{nameError}</div>}
                  <input
                    placeholder="Vardas ir pavardė"
                    onBlur={blurHandler}
                    name="name"
                    value={name}
                    onChange={(e) => nameHandler(e)}
                  />
                </Form.Field>
                <Form.Field>
                  <label>Teams vartotojo vardas</label>
                  {(loginEmailDirty && loginEmailError) && <div style={{ color: "red" }}>{loginEmailError}</div>}
                  <input
                    placeholder="Teams vartotojo vardas"
                    onBlur={blurHandler}
                    name="teams"
                    value={loginEmail}
                    onChange={(e) => teamsLoginHandler(e)}
                  />
                </Form.Field>
                <Form.Field>
                  <label>El. paštas</label>
                  {(contactEmailError) && <div style={{ color: "red" }}>{contactEmailError}</div>}
                  <input
                    placeholder="El. paštas"
                    value={contactEmail}
                    onChange={(e) => emailHandler(e)}
                  />
                </Form.Field>
                <Form.Field>
                  <label>Telefono nr.</label>
                  {(phoneError) && <div style={{ color: "red" }}>{phoneError}</div>}
                  <input
                    placeholder="Telefono nr."
                    value={phone}
                    onChange={(e) => phoneHandler(e)}
                  />
                </Form.Field>
                <Form.Field>
                  <label>Užimtumas (val. per savaitę)</label>
                  {(workHoursError) && <div style={{ color: "red" }}>{workHoursError}</div>}
                  <input
                    placeholder="Užimtumas (val. per savaitę)"
                    value={workHoursPerWeek}
                    onChange={(e) => hoursHandler(e)}
                  />
                </Form.Field>

                <div>
                  <Button
                    icon
                    labelPosition="left"
                    className=""
                    as={NavLink} exact to='/view/teachers'
                  >
                    <Icon name="arrow left" />
                    Atgal
                  </Button>
                  <Button
                    type="submit"
                    className="controls"
                    id='details'
                    disabled={!formValid}
                    onClick={createTeacher}
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
