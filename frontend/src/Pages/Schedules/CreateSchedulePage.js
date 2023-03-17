import React, { useEffect, useState } from "react";
import { NavLink, useHref } from "react-router-dom";
import {
  Button,
  Divider,
  Form,
  Grid,
  Icon,
  Input,
  Segment,
  Select,
} from "semantic-ui-react";
import MainMenu from "../../Components/MainMenu";
import { SchedulesMenu } from "../../Components/SchedulesMenu";
import { DatePicker } from "antd";
import dayjs from "dayjs";
import "antd/dist/reset.css";

const JSON_HEADERS = {
  "Content-Type": "application/json",
};

export function CreateSchedule() {
  const today = dayjs();
  const listUrl = useHref("/create/groupsSchedules/modify/");
  const [groups, setGroups] = useState([]);
  const [groupId, setGroupId] = useState();
  const [status, setStatus] = useState("Valid");
  const [startingDate, setStartingDate] = useState("");
  const [defaultDate, setDefaultDate] = useState(today);

  const applyResult = (result) => {
    if (result.ok) {
      let info = result
        .json()
        .then((jsonResponse) => (window.location = listUrl + jsonResponse.id));
    } else {
      window.alert("Nepavyko sukurti: pavadinimas turi būti unikalus!");
    }
  };

  const createSchedule = () => {
    fetch(
      "/api/v1/schedule?groupId=" + groupId + "&startingDate=" + startingDate,
      {
        method: "POST",
        headers: JSON_HEADERS,
        body: JSON.stringify({
          status,
        }),
      }
    ).then(applyResult);
  };

  useEffect(() => {
    fetch("/api/v1/groups/")
      .then((response) => response.json())
      .then((data) =>
        setGroups(
          data.map((x) => {
            return { key: x.id, text: x.name, value: x.id };
          })
        )
      );
  }, [groups]);

  const formatStartingDate = () => {
    return startingDate === "" ? "" : dayjs(startingDate).format("YYYY-MM-DD");
  };

  return (<div className="create-new-page">
    <MainMenu />

    <Grid columns={2} >

      <Grid.Column width={2} id='main'>

        <SchedulesMenu />

      </Grid.Column>
      <Grid.Column floated='left' textAlign='left' verticalAlign='top' width={13}>
        <Segment id='segment' color='teal'>

          <Form >
            <div style={{ display: 'flex', alignItems: 'center' }}>
              <Form.Field style={{ marginRight: '1em' }}>
                <label>Grupė su programa</label>
                <Select options={groups} placeholder='Grupė su programa' onChange={(e, data) => setGroupId(data.value)} />
              </Form.Field>
              <div style={{ flexShrink: 0, paddingTop: '9px' }}>
                <Button
                  id="icocolor"
                  basic
                  icon="plus"
                  title="Sukurti grupę"
                  href={"/create/groups#/create/groups"}
                />
              </div>
              </div>
              <Form.Field>
                <label>Data nuo</label>
              </Form.Field>
              
            </Form>

            <DatePicker
              className="controls4"
              placeholder="Data nuo"
              onChange={(e) => {
                const newDate = dayjs(e).format("YYYY-MM-DD");
                setStartingDate(newDate);
              }}
            />

          <Divider hidden></Divider>
          
          <Form>
            <div>
              <Button icon labelPosition="left" className="" as={NavLink} exact to='/view/groupsSchedules'><Icon name="arrow left" />Atgal</Button>
              <Button type="submit" onClick={() => createSchedule()} className="" id='details'>Sukurti</Button>
            </div>
          </Form>
            
          </Segment>
        </Grid.Column>
      </Grid>
    </div>
  );
}
