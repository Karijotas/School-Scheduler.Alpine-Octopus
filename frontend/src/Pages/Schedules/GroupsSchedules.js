import React, { useEffect, useState } from "react";
import { NavLink } from "react-router-dom";
import {
  Button,
  ButtonGroup,
  Confirm,
  Divider,
  Grid,
  Icon,
  Input,
  Pagination,
  Segment,
  Table,
} from "semantic-ui-react";
import MainMenu from "../../Components/MainMenu";
import { SchedulesMenu } from "../../Components/SchedulesMenu";
import { format } from "date-fns";
import { DayPicker } from "react-day-picker";
import "react-day-picker/dist/style.css";
import { StatusButton } from "./StatusButton";

const JSON_HEADERS = {
  "Content-Type": "application/json",
};

export function GroupsSchedules() {
  const [nameText, setNameText] = useState("");
  const [dateFrom, setDateFrom] = useState("");
  const [dateUntil, setDateUntil] = useState("");
  const [active, setActive] = useState();
  const [open, setOpen] = useState(false);
  const [activePage, setActivePage] = useState();
  const [pagecount, setPagecount] = useState();
  const [groups, setGroups] = useState([]);
  const [status, setStatus] = useState("");

  const schedules = [
    {
      id: 1,
      name: "Pirmas tvarkaraštis",
      dateFrom: "2016-01-04 10:34:23",
      dateUntil: "2016-06-04 10:34:23",
      status: "invalid",
    },
    {
      id: 2,
      name: "Antras tvarkaraštis",
      dateFrom: "2017-01-04 10:34:23",
      dateUntil: "2018-06-04 10:34:23",
      status: "attention",
    },
    {
      id: 3,
      name: "Trečias tvarkaraštis",
      dateFrom: "2019-01-04 10:34:23",
      dateUntil: "2023-06-04 10:34:23",
      status: "valid",
    },
    {
      id: 4,
      name: "Ketvirtas tvarkaraštis",
      dateFrom: "2022-01-04 10:34:23",
      dateUntil: "2023-06-04 10:34:23",
      status: "valid",
    },
  ];

  // const status = "schedules";
  // const myStatus = () => {
  //   if(schedules.status === "valid"){
  //     return ( <Button
  //     id="okey"
  //     basic
  //     compact
  //     icon="check"
  //     title="Statusas"
  //   ></Button>)
  //   } else if (schedules.status === "invalid"){
  //     return (
  //       <Button
  //     id="grey"
  //     basic
  //     compact
  //     icon="clock outline"
  //     title="Statusas"
  //   ></Button>
  //     )
  //   } else {
  //     return (
  //       <Button
  //     id="attention"
  //     basic
  //     compact
  //     icon="attention"
  //     title="Statusas"
  //   ></Button>
  //     )
  //   }
  // }

  // const fetchGroups = async () => {
  //   fetch("/api/v1/groups/")
  //     .then((response) => response.json())
  //     .then((jsonResponse) => setGroups(jsonResponse));
  // };

  // useEffect(() => {
  //  fetchGroups();
  // }, [activePage]);

  return (
    <div>
      <MainMenu />
      <Grid columns={2}>
        <Grid.Column width={2} id="main">
          <SchedulesMenu />
        </Grid.Column>
        <Grid.Column textAlign="left" verticalAlign="top" width={13}>
          <Segment id="segment" color="teal">
            <div>
              <Input
                title="Filtruoti pagal pavadinimą"
                className="controls1"
                placeholder="Filtruoti pagal pavadinimą"
                value={nameText}
                onChange={(e) => setNameText(e.target.value)}
              />
              <Input
                className="controls1"
                title="Filtruoti nuo"
                placeholder="Filtruoti nuo (MMMM-MM-DD)"
                value={dateFrom}
                onChange={(e) => setDateFrom(e.target.value)}
                required={dateUntil}
              />
              <Input
                className="controls1"
                title="Filtruoti iki"
                placeholder="Filtruoti iki (MMMM-MM-DD)"
                value={dateUntil}
                onChange={(e) => setDateUntil(e.target.value)}
              />
              <Divider horizontal hidden></Divider>

              <Table selectable>
                <Table.Header>
                  <Table.Row>
                    <Table.HeaderCell>
                      Grupės/ tvarkaraščio pavadinimas
                    </Table.HeaderCell>
                    <Table.HeaderCell>Data nuo</Table.HeaderCell>
                    <Table.HeaderCell>Data iki</Table.HeaderCell>
                    <Table.HeaderCell>Statusas</Table.HeaderCell>
                    <Table.HeaderCell>Veiksmai</Table.HeaderCell>
                  </Table.Row>
                </Table.Header>

                <Table.Body>
                  {schedules.map((schedule) => (
                    <Table.Row key={schedule.id}>
                      <Table.Cell>{schedule.name}</Table.Cell>
                      <Table.Cell>{schedule.dateFrom}</Table.Cell>
                      <Table.Cell>{schedule.dateUntil}</Table.Cell>
                      <Table.Cell collapsing> Statusas</Table.Cell>
                      <Table.Cell collapsing>
                      <Button
                        id="icocolor"
                            basic
                            compact
                            icon="eye"
                            title="Peržiūrėti"
                            href={"#/view/groupsschedules/edit/" + schedule.id}
                            onClick={() => setActive(schedule.id)}
                          ></Button>
                          <Button
                          id="icocolor"
                            basic
                            compact
                            title="Suarchyvuoti"
                            icon="archive"
                            onClick={() => setOpen(schedule.id)}
                          ></Button>
                        <Confirm
                          open={open}
                          header="Dėmesio!"
                          content="Ar tikrai norite perkelti į archyvą?"
                          cancelButton="Grįžti atgal"
                          confirmButton="Taip"
                          onCancel={() => setOpen(false)}
                          // onConfirm={() => removeGroup(open)}
                          size="small"
                        />
                      </Table.Cell>
                    </Table.Row>
                  ))}
                </Table.Body>
              </Table>
              <Divider hidden></Divider>

              <ButtonGroup compact basic>
                <Button
                  title="Atgal"
                  onClick={() =>
                    setActivePage(activePage <= 0 ? activePage : activePage - 1)
                  }
                  icon
                >
                  <Icon name="arrow left" />{" "}
                </Button>
                {/* {[...Array(pagecount)].map((e, i) => {
                  return (
                    <Button
                      title={i + 1}
                      key={i}
                      active={activePage === i ? true : false}
                      onClick={() => setActivePage(i)}
                    >
                      {i + 1}
                    </Button>
                  );
                })} */}
                <Button
                  title="Pirmyn"
                  onClick={() =>
                    setActivePage(
                      activePage >= pagecount - 1 ? activePage : activePage + 1
                    )
                  }
                  icon
                >
                  <Icon name="arrow right" />{" "}
                </Button>
              </ButtonGroup>
            </div>
          </Segment>
        </Grid.Column>
      </Grid>
    </div>
  );
}
