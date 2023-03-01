import React, { useEffect, useState } from "react";
import {
  Button,
  Divider,
  Icon,
  Input,
  Table,
  Grid,
  Segment,
  List,
  Form,
  Select,
} from "semantic-ui-react";
import { ViewTeachers } from "./ViewTeachers";
import MainMenu from "../../../Components/MainMenu";
import { EditMenu } from '../../../Components/EditMenu';
import { useParams } from "react-router-dom";

const JSON_HEADERS = {
  "Content-Type": "application/json",
};

export function EditTeacherObject() {
  const params = useParams();
  const [modules, setModules] = useState([]);
  
  const [teacher, setTeacher] = useState("");
  const [teacherId, setTeacherId] = useState("");

  const [hide, setHide] = useState(false);
  const [active, setActive] = useState(true);
  const [error, setError] = useState();
  const [subjectId, setSubjectId] = useState("");
  const [teacherShifts, setTeacherShifts] = useState([]);
  const [teacherSubjects, setTeacherSubjects] = useState([]);
  const [teachers, setTeachers] = useState({
    name: "",
    loginEmail: "",
    contactEmail: "",
    phone: "",
    workHoursPerWeek: "",
    shift: "",
    createdDate: "",
    modifiedDate: "",
  });

   //Validation
   const [name, setName] = useState('');
  

   const [nameError, setNameError] = useState("")
   const [loginEmailError, setloginEmailError] = useState("")
   const [contactEmailError, setContactEmailError] = useState("")
   const [phoneError, setPhoneError] = useState("")
   const [workHoursError, setWorkHoursError] = useState("")
   
   const [formValid, setFormValid] = useState(false)

// BUTTON DISABLER
   useEffect(() => {
     if(nameError || loginEmailError || contactEmailError || workHoursError) {
       setFormValid(false)
     } else {
       setFormValid(true)
     }
   }, [nameError, loginEmailError, contactEmailError, workHoursError])
 
 
//Name validation
   const handleNameInputChange = (e) => {
    teachers.name = e.target.value;
    setName(e.target.value);
    validateNameInput(e.target.value)
  };

   const validateNameInput = (value) => {
     if(!/^[\p{L}ĄČĘĖĮŠŲŪŽąčęėįšųūž\s-]+$/iu.test(value)){
       setNameError("Įveskite tik raides")
       if(!value){
         setNameError("Negali būti tuščias!")
       }
     } else if (value.length <2 || value.length > 40){
       setNameError("Įveskite nuo 2 iki 40 simbolių!")
       
     } else {
       setNameError("") 
     }
    }
// LOGIN EMAIL VALIDATION
  const handleLoginEmailInputChange = (e) => {
    teachers.loginEmail = e.target.value;
    setName(e.target.value);
    validateLoginEmailInput(e.target.value)
  };   

  const validateLoginEmailInput = (value) => {
    if(!/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test(value)){
       setloginEmailError("Neteisingas pašto formatas!")
       if(!value){
         setloginEmailError("Negali būti tuščias!")
       }
     } else {
       setloginEmailError("")
     }
    }
 
// contact email validation
const handleContactEmailInputChange = (e) => {
  teachers.contactEmail = e.target.value;
  setName(e.target.value);
  validateContactEmailInput(e.target.value)
};   

const validateContactEmailInput = (value) => {
  if(!/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test(value)){
    setContactEmailError("Neteisingas pašto formatas!")
    if(!value){
      setContactEmailError("")
    }
   }
}
 
// phone validation
const handlePhoneInputChange = (e) => {
  teachers.phone = e.target.value;
  setName(e.target.value);
  validatePhoneInput(e.target.value)
};   

const validatePhoneInput = (value) => {
  if(!/^(\+370|8)\d{8}$/.test(value)){
         setPhoneError("Neteisingas numerio formatas!")
         if(!value){
          setPhoneError("")
         }
       }
}

const handleWorkHoursInputChange = (e) => {
  teachers.workHoursPerWeek = e.target.value;
  setName(e.target.value);
  validateWorkHoursInput(e.target.value)
};   

const validateWorkHoursInput = (value) => {
  if(!/^\d+$/.test(value)){
         setWorkHoursError("Įveskite tik skaičius")
         if(!value){
          setWorkHoursError("")
         }
       }
}
 
  //    setWorkHoursPerWeek(e.target.value)
     
  //    if(!/^\d+$/.test(e.target.value)){
  //      setWorkHoursError("Įveskite tik skaičius")
  //      if(!e.target.value){
  //        setWorkHoursError("")
  //      }
  //    }
  //  }

  useEffect(() => {
    fetch("/api/v1/teachers/" + params.id)
      .then((response) => response.json())
      .then(setTeachers);
  }, [active, params]);

  const applyResult = () => {
    setActive(true);
  };

  const fetchTeacherShifts = ()  => {
    fetch(`/api/v1/teachers/${params.id}/shifts`)
      .then((response) => response.json())
      .then(setTeacherShifts)
      .then(console.log(teacherShifts));
  };

  useEffect(() => {
    fetch(`/api/v1/teachers/${params.id}/shifts`)
      .then((response) => response.json())
      .then(setTeacherShifts)
      .then(console.log(teacherShifts));
  }, [params]);

  const fetchTeacherSubjects = ()  => {
    fetch(`/api/v1/teachers/${params.id}/subjects`)
      .then((response) => response.json())
      .then(setTeacherSubjects)
      .then(console.log(teacherSubjects));
  };

  useEffect(() => {
    fetch(`/api/v1/teachers/${params.id}/subjects`)
      .then((response) => response.json())
      .then(setTeacherSubjects)
      .then(console.log(teacherSubjects));
  }, [params]);


  const updateTeachers = () => {
    fetch("/api/v1/teachers/" + params.id, {
      method: "PUT",
      headers: JSON_HEADERS,
      body: JSON.stringify(teachers),
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
    setTeachers({
      ...teachers,
      [property]: event.target.value,
    });
  };

  const editThis = () => {
    setActive(false);
  };

  useEffect(() => {
    fetch("/api/v1/subjects/")
      .then((response) => response.json())
      .then((data) =>
        setModules(
          data.map((x) => {
            return { key: x.id, text: x.name, value: x.id };
          })
        )
      );
  }, []);

//   useEffect(() => {
//     fetch("/api/v1/shifts/")
//       .then((response) => response.json())
//       .then((data) =>
//         setShifts(
//           data.map((x) => {
//             return { key: x.id, text: x.name, value: x.id };
//           })
//         )
//       );
//   }, []);


  return (
    <div>
      <MainMenu />
      <Grid columns={2}>
        <Grid.Column width={2} id="main">
          <EditMenu active="teachers" />
        </Grid.Column>
        <Grid.Column
          floated="left"
          textAlign="left"
          verticalAlign="top"
          width={13}
        >
          <Segment raised color="teal">
            {active && !hide && (
              <div>
                <Table celled color="violet">
                  <Table.Header>
                    <Table.Row>
                      <Table.HeaderCell>Vardas ir pavardė</Table.HeaderCell>
                      <Table.HeaderCell>Telefono nr.</Table.HeaderCell>
                      <Table.HeaderCell>Paskutinis atnaujinimas:</Table.HeaderCell>
                      <Table.HeaderCell>Veiksmai</Table.HeaderCell>
                    </Table.Row>
                  </Table.Header>

                  <Table.Body>
                    <Table.Row>
                      <Table.Cell>{teachers.name}</Table.Cell>
                      <Table.Cell>{teachers.phone}</Table.Cell>
                      <Table.Cell collapsing>
                        {" "}
                        {teachers.modifiedDate}{" "}
                      </Table.Cell>
                      <Table.Cell collapsing>
                        <Button onClick={editThis}>Redaguoti</Button>
                      </Table.Cell>
                    </Table.Row>
                  </Table.Body>
                </Table>
                <Table celled color="violet">
                  <Table.Header>
                    <Table.Row>
                      <Table.HeaderCell>Teams vartotojo vardas</Table.HeaderCell>
                      <Table.HeaderCell>Email</Table.HeaderCell>
                      <Table.HeaderCell>Užimtumas (val. per savaitę)</Table.HeaderCell>
                    </Table.Row>
                  </Table.Header>

                  <Table.Body>
                    <Table.Row>
                      <Table.Cell>{teachers.loginEmail}</Table.Cell>
                      <Table.Cell>{teachers.contactEmail}</Table.Cell>
                      <Table.Cell>{teachers.workHoursPerWeek}</Table.Cell>
                    </Table.Row>
                  </Table.Body>
                </Table>

                <Grid columns={2} divided>
                  <Grid.Column>
                    <Table width={6}>
                      <Table.Header>
                        <Table.Row>
                          <Table.HeaderCell width={6}>
                            Pamainos:
                          </Table.HeaderCell>
                        </Table.Row>
                      </Table.Header>
                      <Table.Body>
                        {teacherShifts.map((shift) => (
                          <Table.Row key={shift.id}>
                            <Table.Cell>{shift.name}</Table.Cell>
                          </Table.Row>
                        ))}
                      </Table.Body>
                    </Table>
                  </Grid.Column>
                  <Grid.Column>
                    <Table width={6}>
                      <Table.Header>
                        <Table.Row>
                          <Table.HeaderCell width={6}>
                            Dalykai:
                          </Table.HeaderCell>
                        </Table.Row>
                      </Table.Header>
                      <Table.Body>
                        {teacherSubjects.map((subject) => (
                          <Table.Row key={subject.id}>
                            <Table.Cell>{subject.name}</Table.Cell>
                          </Table.Row>
                        ))}
                      </Table.Body>
                    </Table>
                  </Grid.Column>
                  </Grid>

                
                <Divider hidden />
                <Button
                  icon
                  labelPosition="left"
                  className=""
                  href="#/view/subjects"
                >
                  <Icon name="arrow left" />
                  Atgal
                </Button>
              </div>
            )}
            {!active && !hide && (
              <div>
                <Table celled color="violet">
                  <Table.Header>
                    <Table.Row>
                    <Table.HeaderCell>Vardas ir pavardė</Table.HeaderCell>
                      <Table.HeaderCell>Teams vartotojo vardas</Table.HeaderCell>
                      <Table.HeaderCell>Email</Table.HeaderCell>
                      <Table.HeaderCell>Telefono nr.</Table.HeaderCell>
                      <Table.HeaderCell>Užimtumas (val. per savaitę)</Table.HeaderCell>
                      <Table.HeaderCell>Paskutinis atnaujinimas:</Table.HeaderCell>
                      <Table.HeaderCell>Veiksmai</Table.HeaderCell>
                    </Table.Row>
                  </Table.Header>

                  <Table.Body>
                    <Table.Row>
                      <Table.Cell collapsing>
                      {(nameError) && <div style={{color: "red"}}>{nameError}</div>}
                        <Input
                          value={teachers.name}
                          onChange={(e) => handleNameInputChange(e)}
                        />
                        
                      </Table.Cell>
                      <Table.Cell collapsing>
                      {(loginEmailError) && <div style={{color: "red"}}>{loginEmailError}</div>}
                        <Input
                          value={teachers.loginEmail} 
                          onChange={(e) => handleLoginEmailInputChange(e)}
                        />
                      </Table.Cell>
                      <Table.Cell collapsing>
                      {(contactEmailError) && <div style={{color: "red"}}>{contactEmailError}</div>}
                        <Input
                          value={teachers.contactEmail} 
                          onChange={(e) => handleContactEmailInputChange(e)}
                        />
                      </Table.Cell>
                      <Table.Cell collapsing>
                      {(phoneError) && <div style={{color: "red"}}>{phoneError}</div>}
                        <Input
                          value={teachers.phone} 
                          onChange={(e) => handlePhoneInputChange(e)}
                        />
                      </Table.Cell>
                      <Table.Cell collapsing>
                      {(workHoursError) && <div style={{color: "red"}}>{workHoursError}</div>}
                        <Input
                          value={teachers.workHoursPerWeek} 
                          onChange={(e) => handleWorkHoursInputChange(e)}
                        />
                      </Table.Cell>
                      <Table.Cell collapsing>
                        {" "}
                        {teachers.modifiedDate}{" "}
                      </Table.Cell>
                    </Table.Row>
                  </Table.Body>
                </Table>
                <Divider hidden></Divider>
                <Button onClick={() => setActive(true)}>Atšaukti</Button>
                <Button disabled={!formValid} floated="right" primary onClick={updateTeachers}>
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
