import React, { useEffect, useState } from "react";
import { NavLink, useHref } from "react-router-dom";
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
import MainMenu from "../../Components/MainMenu";
import { SchedulesMenu } from "../../Components/SchedulesMenu";

const JSON_HEADERS = {
  "Content-Type": "application/json",
};

export function CreateSchedulePageNext() {
  const params = useParams();
  const [roomId, setRoomId] = useState("");
  const [rooms, setRooms] = useState([]);
  const [teacherId, setTeacherId] = useState("");
  const [teachers, setTeachers] = useState([]);
  const [subjects, setSubjects] = useState([]);
  const [lessonId, setLessonId] = useState();
  const [groups, setGroups] = useState([]);
  const [program, setProgram] = useState({});
  const [teachersInSubjects, setTeachersInSubjects] = useState([]);
  const [okey, setOkey] = useState("");

  const [subjectsInProgram, setSubjectsInProgram] = useState([]);
  const [schedule, setSchedule] = useState(
    {}
    // name: "",
    // startingDate: "",
    // plannedTillDate: "",
    // status: "",
    // lessons: [],
    // group: "",
    // shift: "",
    // groupName: "",
    // shiftName: "",
    // groupIdValue: "",
  );

  const applyResult = (result) => {
    if (result.ok) {
      setOkey("Sėkmingai priskirta");
      setTimeout(() => {
        setOkey("");
      }, 5000);
    } else {
      window.alert("Nepavyko sukurti: pavadinimas turi būti unikalus!");
    }
  };

  useEffect(() => {
    fetch(`/api/v1/schedule/${params.id}/subjects`)
      .then((response) => response.json())
      .then(setSubjects);
  }, [params]);

  useEffect(() => {
    fetch("/api/v1/schedule/" + params.id)
      .then((response) => response.json())
      .then((result) => {
        getGroup(result);
      });
  }, []);

  // useEffect(() => {
  //   console.log(schedule)
  //   console.log(teacherId, roomId, lessonId)
  // }, [teacherId, roomId, lessonId])

  // useEffect(() => {
  //   fetch("/api/v1/groups/" + schedule.groupIdValue)
  //     .then((response) => response.json())
  //     .then(setGroups);
  // }, [schedule]);

  // const updateSchedule = () => {
  //   fetch('/api/v1/schedule/' + params.id + '?lessonId=' + lessonId + '&teacherId='+ teacherId + '&roomId=' + roomId,  {
  //       method: 'PATCH',
  //       headers: JSON_HEADERS,
  //       body: JSON.stringify(schedule)
  //   })
  // };

  const updateSchedule = (lessonId) => {
    if (teacherId === undefined){
      fetch(`/api/v1/schedule/${params.id}/${lessonId}/?roomId=${roomId}`, {
        method: "PATCH",
      }).then(applyResult);
    } else  if (roomId === undefined){
      fetch(`/api/v1/schedule/${params.id}/${lessonId}/?teacherId=${teacherId}`, {
        method: "PATCH",
      }).then(applyResult);
    } else {
    fetch(`/api/v1/schedule/${params.id}/${lessonId}/?roomId=${roomId}&teacherId=${teacherId}`, {
      method: "PATCH",
    }).then(applyResult);
  }};

  //Teacher dropdown
  useEffect(() => {
    fetch("/api/v1/teachers/")
      .then((response) => response.json())
      .then((data) =>
        setTeachers(
          data.map((x) => {
            return { key: x.id, text: x.name, value: x.id };
          })
        )
      );
  }, []);

  const getGroup = (props) => {
    fetch(`/api/v1/groups/${props.groupIdValue}`)
      .then((response) => response.json())
      .then((data) => {
        setGroups(data);
      });
  };

  //   const getProgramInGroup = (props)  =>  {
  //     fetch(`/api/v1/programs/${props.programId}`)
  //       .then((response) => response.json())
  //       .then(data => {getSubjectsInProgram(data) && setProgram(data)})
  // };

  // const getSubjectsInProgram = (props)  => {
  //     fetch(`/api/v1/programs/${props.id}/subjects`)
  //       .then((response) => response.json())
  //       .then(setSubjectsInProgram);
  //     };

  // Room dropdown
  useEffect(() => {
    fetch("/api/v1/rooms/")
      .then((response) => response.json())
      .then((data) =>
        setRooms(
          data.map((x) => {
            return { key: x.id, text: x.name, value: x.id };
          })
        )
      );
  }, []);

  const getTeachersInSubjects = (props) => {
    fetch(`/api/v1/subjects/${props.id}/teachers`)
      .then((response) => response.json())
      .then(setTeachersInSubjects)
      .then(console.log(teachersInSubjects));
  };

  const checkAndSetStatus = () => {
    fetch(`/api/v1/schedule/validation/${params.id}`, {
      method: "PATCH",
    }).then(applyResult);
  }

  return (
    <div className="create-new-page">
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
            <div>
              <Table celled>
                <Table.Header>
                  <Table.HeaderCell colspan="3">
                    Programos pavadinimas
                  </Table.HeaderCell>
                </Table.Header>

                <Table.Row>
                  <Table.Cell>{groups.programName}</Table.Cell>
                  {console.log(schedule)}
                </Table.Row>

                <Table.Header>
                  <Table.HeaderCell colspan="3">Dalykai</Table.HeaderCell>
                  {okey && (
                    <Message size="tiny" color="teal">
                      {okey}
                    </Message>
                  )}
                </Table.Header>

                {subjects.map((subject) => (
                  <Table.Row key={subject.id}>
                    {console.log(subject.subject.id)}
                    <Table.Cell>{subject.subject.name}</Table.Cell>
                    <Table.Cell>
                      <Select
                        options={teachers}
                        placeholder="Mokytojai"
                        onChange={(e, data) => setTeacherId(data.value)}
                      />
                    </Table.Cell>
                    <Table.Cell>
                      <Select
                        options={subject.subject.subjectRooms.map((x) => {
                          return { key: x.id, text: x.name, value: x.id };
                        })}
                        placeholder="Kabinetai"
                        onChange={(e, data) => setRoomId(data.value)}
                      />
                    </Table.Cell>
                    <Table.Cell>
                      <Button
                        className="controls"
                        onClick={() => updateSchedule(subject.id)}
                        id="details"
                      >
                        Priskirti
                      </Button>
                    </Table.Cell>
                  </Table.Row>
                ))}
              </Table>

              <Divider horizontal hidden></Divider>

              <Button
                icon
                labelPosition="left"
                className=""
                as={NavLink}
                exact
                to={"/create/groupsSchedules/"}
              >
                <Icon name="arrow left" />
                Atgal
              </Button>
              <Button
                className="controls"
                id="details"
                as={NavLink}
                exact
                to={"/view/groupsSchedules/edit/" + params.id}
                onClick={() => checkAndSetStatus()}
              >
                Tęsti
              </Button>
            </div>
          </Segment>
        </Grid.Column>
      </Grid>
    </div>
  );
}
