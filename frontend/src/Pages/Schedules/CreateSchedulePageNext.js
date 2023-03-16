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
  const [roomId, setRoomId] = useState();
  const [rooms, setRooms] = useState([]);
  const [teacherId, setTeacherId] = useState();
  const [teachers, setTeachers] = useState([]);
  const [lessonId, setLessonId] = useState();
  const [groups, setGroups] = useState({    
    name: "",
    studentAmount: "",
    schoolYear: "",
    programName: "",
    shift: "",
    modifiedDate: "",
  });
  const [schedule, setSchedule] = useState({
    name: "",
    startingDate: "",
    plannedTillDate: "",
    status: "",
    lessons: [],
    group: "",
    shift: "",
    groupIdValue: "",
    shiftName: "",
    groupIdValue: "",
  });
  const [subjectId, setSubjectId] = useState("");

  useEffect(() => {
    fetch("/api/v1/schedule/" + params.id)
      .then((response) => response.json())
      .then(setSchedule);
  }, [params]);

  // useEffect(() => {
  //   console.log(schedule)
  //   console.log(teacherId, roomId, lessonId)
  // }, [teacherId, roomId, lessonId])

  useEffect(() => {
    fetch("/api/v1/groups/" + schedule.groupIdValue)
      .then((response) => response.json())
      .then(setGroups);
  }, [schedule]);

  // const updateSchedule = () => {
  //   fetch('/api/v1/schedule/' + params.id + '?lessonId=' + lessonId + '&teacherId='+ teacherId + '&roomId=' + roomId,  {
  //       method: 'PATCH',
  //       headers: JSON_HEADERS,
  //       body: JSON.stringify(schedule)
  //   })
  // };

  const updateSchedule = () => {
    fetch(
      `/api/v1/schedule/${params.id}/${lessonId}?roomId=${roomId}&teacherId=${teacherId}`,
      {
        method: "PATCH",
        headers: JSON_HEADERS,
        body: JSON.stringify(schedule),
      }
    );
  };

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
  }, [teachers]);

  //Room dropdown
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
  }, [rooms]);

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
                  <Table.Row>
                    <Table.HeaderCell>Programos pavadinimas </Table.HeaderCell>
                  </Table.Row>
                </Table.Header>

                {/* <Table.Body>
                  <Table.Row>
                    <Table.Cell>{groups.programName}</Table.Cell>
                  </Table.Row>
                </Table.Body>
              </Table>
              <Table>
                <Table.Header>
                  <Table.Row>
                    <Table.HeaderCell>Dalykai</Table.HeaderCell>
                  </Table.Row>
                </Table.Header>
                <Table.Body>
                  {schedule.lessons.map((lesson) => (
                    <Table.Row key={lesson.id}>
                      <Table.Cell>{lesson.subject.name}</Table.Cell>
                      <Table.Cell>
                        <Select
                          options={teachers}
                          placeholder="Mokytojai"
                          onChange={(e, data) => setTeacherId(data.value)}
                        />
                      </Table.Cell>
                      <Table.Cell>
                        <Select
                          options={rooms}
                          placeholder="Kabinetai"
                          onChange={(e, data) => setRoomId(data.value)}
                        />
                      </Table.Cell>
                      <Table.Cell>
                        <Button
                          className="controls"
                          onClick={updateSchedule}
                          id="details"
                        >
                          Set
                        </Button>
                      </Table.Cell>
                    </Table.Row>
                  ))}
                </Table.Body> */}
              </Table>

              <Divider horizontal hidden></Divider>

              <Button
                icon
                labelPosition="left"
                className=""
                as={NavLink}
                exact
                to="/create/groupsSchedules"
              >
                <Icon name="arrow left" />
                Atgal
              </Button>
              <Button
                className="controls"
                onClick={updateSchedule}
                id="details"
              >
                Testi
              </Button>
            </div>
          </Segment>
        </Grid.Column>
      </Grid>
    </div>
  );
}
