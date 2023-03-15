import React, { useEffect, useState } from "react";
import { NavLink, useHref } from 'react-router-dom';
import { useParams } from 'react-router-dom';
import { Button, Divider, Grid, Icon, Input, Message, Segment, Select, Table } from 'semantic-ui-react';
import MainMenu from '../../Components/MainMenu';
import { SchedulesMenu } from '../../Components/SchedulesMenu';

const JSON_HEADERS = {
  "Content-Type": "application/json",
};

export function CreateSchedulePageNext() {
  const params = useParams();
  const [roomId, setRoomId] = useState()
  const [rooms, setRooms] = useState([])
  const [teacherId, setTeacherId] = useState()
  const [teachers, setTeachers] = useState([])
  const [lessonId, setLessonId] = useState();
  const [schedule, setSchedule] = useState({
    name: "",
    startingDate: "",
    plannedTillDate: "",
    status: "",
    lessons: [],
    group: "",
    shift: "",
    groupName: "",
    shiftName: "",
  });


  useEffect(() => {
    fetch('/api/v1/schedule/' + params.id)
      .then(response => response.json())
      .then(setSchedule);
  }, [params]);

  useEffect(() => {
    console.log(schedule)
    console.log(teacherId, roomId, lessonId)
  }, [teacherId, roomId, lessonId])


  // const updateSchedule = () => {
  //   fetch('/api/v1/schedule/' + params.id + '?lessonId=' + lessonId + '&teacherId='+ teacherId + '&roomId=' + roomId,  {
  //       method: 'PATCH',
  //       headers: JSON_HEADERS,
  //       body: JSON.stringify(schedule)
  //   })
  // };

  const updateSchedule = () => {
    fetch(`/api/v1/${params.id}/${lessonId}?teacherId=${teacherId}&roomId=${roomId}`, {
      method: 'PATCH',
      headers: JSON_HEADERS,
      body: JSON.stringify(schedule)
    })
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

  return (<div className="create-new-page">

    <MainMenu />

    <Grid columns={2} >
      <Grid.Column width={2} id='main'>

        <SchedulesMenu />

      </Grid.Column>
      <Grid.Column floated='left' textAlign='left' verticalAlign='top' width={13}>
        <Segment id='segment' color='teal'>

          <div >

            <Table celled >
              <Table.Header >
                <Table.HeaderCell colspan="3">Programos pavadinimas </Table.HeaderCell>
              </Table.Header>

              <Table.Row>
                <Table.Cell>pavadinimas</Table.Cell>
              </Table.Row>

              <Table.Header >
                <Table.HeaderCell colspan="3" >Dalykai</Table.HeaderCell>
              </Table.Header>

              {schedule.lessons.map((lesson) =>
                <Table.Row key={lesson.id}>

                  <Table.Cell>{lesson.subject.name}</Table.Cell>
                  <Table.Cell><Select options={teachers} placeholder='Mokytojai' onChange={(e, data) => setTeacherId(data.value)} /></Table.Cell>
                  <Table.Cell><Select options={rooms} placeholder='Kabinetai' onChange={(e, data) => setRoomId(data.value)} /></Table.Cell>
                  <Table.Cell><Button className='controls' onClick={updateSchedule} id='details' >Set</Button></Table.Cell>

                </Table.Row>
              )}
            </Table>

            <Divider horizontal hidden></Divider>

            <Button icon labelPosition="left" className="" as={NavLink} exact to='/create/groupsSchedules'><Icon name="arrow left" />Atgal</Button>
            <Button className='controls' onClick={updateSchedule} id='details' >Testi</Button>

          </div>
        </Segment>
      </Grid.Column>
    </Grid>
  </div>
  );
}
