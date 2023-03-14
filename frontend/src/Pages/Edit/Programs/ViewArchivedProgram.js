import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import {
  Button,
  Divider,
  Form,
  Grid,
  Icon,
  Input,
  List,
  Segment,
  Select,
  Table,
  TextArea,
} from "semantic-ui-react";
import MainMenu from "../../../Components/MainMenu";
import  {EditMenu} from "../../../Components/EditMenu";

const JSON_HEADERS = {
  "Content-Type": "application/json",
};

export function ViewArchivedProgram() {

  const params = useParams(); 
  const [active, setActive] = useState(true);
  const [subjects, setSubjects] = useState([]);
  const [subjectsInProgram, setSubjectsInProgram] = useState([]);
  const [subject, setSubject] = useState("");
  const [subjectHours, setSubjectHours] = useState("");
  const [totalHours, setTotalHours] = useState("");
  const [subjectId, setSubjectId] = useState("");
  const [programName, setProgramName] = useState("");
  const [programDescription, setProgramDescription] = useState("");

  const [error, setError] = useState();
  const [programs, setPrograms] = useState([]);


  useEffect(() => {
    fetch("/scheduler/api/v1/programs/" + params.id)
      .then((response) => response.json())
      .then(setPrograms);
  }, [totalHours, active, params]);

  useEffect(() => {
    fetch(`/scheduler/api/v1/programs/${params.id}/subjects`)
      .then((response) => response.json())
      .then(setSubjectsInProgram)
      .then(console.log(subjectsInProgram));
  }, [params, totalHours, subjectHours]);

  const getSubjectsInProgram = () => {
    fetch(`/scheduler/api/v1/programs/${params.id}/subjects`)
      .then((response) => response.json())
      .then(setSubjectsInProgram)
      .then(console.log(subjectsInProgram));
  };

  useEffect(() => {
    fetch(`/scheduler/api/v1/programs/${params.id}/subjects/hours`)
      .then((response) => response.json())
      .then(setTotalHours);
  }, [subjectsInProgram]);

  const removeSubject = (programId, subjectId, hours) => {
    fetch(`/scheduler/api/v1/programs/${programId}/subjects/${subjectId}/${hours}`, {
      method: "DELETE",
      headers: JSON_HEADERS,
    })
      .then(getSubjectsInProgram)
      .then((response) => response.json())
      .then(setTotalHours);
  };



  const addSubjectAndHours = (programId, subjectId, hours) => {
    fetch(
      `/scheduler/api/v1/programs/${programId}/subjects/${subjectId}/${hours}/newSubjectsWithHours`,
      {
        method: "POST",
        header: JSON_HEADERS,
        body: JSON.stringify({
          programId,
          subject,
          subjectHours,
        }),
      }
    )
      .then(getSubjectsInProgram)
      .then((response) => response.json())
      .then(setSubjectsInProgram)      
      .then((response) => response.json())
      .then(setTotalHours)
      .then(updatePrograms)
      .then(setSubject(''))
      .then(setSubjectHours(''));
  };



  useEffect(() => {
    fetch(`/scheduler/api/v1/programs/${params.id}/availableSubjects`)
      .then((response) => response.json())
      .then((data) =>
        setSubjects(
          data.map((x) => {
            return { key: x.id, text: x.name, value: x.id };
          })
        )
      );
  }, [totalHours]);

  const applyResult = () => {
    setActive(true);
  };

  const restoreProgram = (id) => {
    fetch("/scheduler/api/v1/programs/restore/" + id, {
      method: "PATCH",
    })
    .then(applyResult)
  };


  const updatePrograms = () => {
    fetch("/scheduler/api/v1/programs/" + params.id, {
      method: "PATCH",
      headers: JSON_HEADERS,
      body: JSON.stringify(programs),
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
    setPrograms({
      ...programs,
      [property]: event.target.value,
    });
  };

  const [updated, setUpdated] = useState();

    useEffect(() => {
        setUpdated(true);
    }, [setUpdated]);


  const editThis = () => {
    setActive(false);
  };
  // const removeGroup = (id) => {
  //     fetch('/api/v1/groups/' + params.id, {
  //         method: 'DELETE',
  //         headers: JSON_HEADERS
  //     })
  //     .then(() => window.location = listUrl);
  // }

  return (
    <div>
      <MainMenu />

      <Grid columns={2}>
        <Grid.Column width={2} id="main">
          <EditMenu />
        </Grid.Column>
        <Grid.Column textAlign="left" verticalAlign="top" width={13}>
          <Segment id="segment" raised color="teal">
            {active && (
              <div>
                <Table celled>
                  <Table.Header>
                    <Table.Row>
                      <Table.HeaderCell>Programos pavadinimas</Table.HeaderCell>                      
                      <Table.HeaderCell>
                        Archyvavimo data
                      </Table.HeaderCell>
                      <Table.HeaderCell>Veiksmai</Table.HeaderCell>
                    </Table.Row>
                  </Table.Header>

                  <Table.Body>
                    <Table.Row>
                      <Table.Cell disabled>{programs.name}</Table.Cell>             

                      <Table.Cell collapsing disabled>
                        {" "}
                        {programs.modifiedDate}{" "}
                      </Table.Cell>

                      <Table.Cell collapsing>
                        <Button  id='details' onClick={() => restoreProgram(params.id)}>Atstatyti</Button>
                      </Table.Cell>
                    </Table.Row>
                  </Table.Body>
                </Table>
                <Divider hidden />
                <Table>
                  <Table.Header>
                    <Table.Row>
                    <Table.HeaderCell>Aprašymas</Table.HeaderCell>
                    </Table.Row>
                  </Table.Header>
                  <Table.Body>
                    <Table.Row>
                    <Table.Cell disabled>{programs.description}</Table.Cell>
                    </Table.Row>
                  </Table.Body>
                </Table>
                <Divider hidden />
                <Table>
                  <Table.Header>
                    <Table.Row>
                      <Table.HeaderCell width={6}>
                        Programos dalykai
                      </Table.HeaderCell>
                      <Table.HeaderCell width={8}></Table.HeaderCell>
                    </Table.Row>
                  </Table.Header>
                  <Table.Body>
                    {subjectsInProgram.map((subject) => (
                      <Table.Row key={subject.id}>
                        <Table.Cell disabled>{subject.subject.name}</Table.Cell>
                        <Table.Cell disabled>{subject.subjectHours} val.</Table.Cell>
                      </Table.Row>
                    ))}
                    <Table.Row>
                      <Table.Cell>
                        <h5>Programos valandų skaičius:</h5>
                      </Table.Cell>
                      <Table.Cell>
                        <h5 disabled>{totalHours} val.</h5>
                      </Table.Cell>
                    </Table.Row>
                  </Table.Body>
                </Table>
                <Button
                  icon
                  labelPosition="left"
                  className=""
                  href="#/view/programs"
                >
                  <Icon name="arrow left" />
                  Atgal
                </Button>
              </div>
            )}          
              
          </Segment>
        </Grid.Column>
      </Grid>
    </div>
  );
}
