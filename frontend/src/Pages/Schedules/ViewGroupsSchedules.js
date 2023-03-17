import { DatePicker } from "antd";
import "antd/dist/reset.css";
import dayjs from "dayjs";
import React, { useEffect, useState } from "react";
import {
  Button,
  ButtonGroup,
  Confirm,
  Divider,
  Grid,
  Icon,
  Input, Segment,
  Table
} from "semantic-ui-react";
import MainMenu from "../../Components/MainMenu";
import { SchedulesMenu } from "../../Components/SchedulesMenu";
const { RangePicker } = DatePicker;

const JSON_HEADERS = {
  "Content-Type": "application/json",
};

export function ViewGroupsSchedules() {
  const today = dayjs();
  const [nameText, setNameText] = useState("");
  const [startingDate, setStartingDate] = useState("");
  const [plannedTillDate, setPlannedTillDate] = useState("");
  const [active, setActive] = useState();
  const [open, setOpen] = useState(false);
  const [pagecount, setPagecount] = useState();
  const [groups, setGroups] = useState([]);
  const [status, setStatus] = useState("");
  const [schedules, setSchedules] = useState([]);
  const [activePage, setActivePage] = useState(0);
  const [schedule, setSchedule] = useState("");
  const [defaultDate, setDefaultDate] = useState(today);
  const [dates, setDates] = useState([]);

  const fetchSchedules = async () => {
    fetch("/alpine-octopus/api/v1/schedule/page?page=" + activePage)
      .then((response) => response.json())
      .then((jsonResponse) => setSchedules(jsonResponse));
  };

  const removeSchedule = (id) => {
    fetch("/alpine-octopus/api/v1/schedule/delete/" + id, {
      method: "PATCH",
    })
      .then(fetchSchedules)
      .then(setOpen(false));
  };

  const fetchFilterSchedulesByName = async () => {
    fetch(`/alpine-octopus/api/v1/schedule/page/name-filter/${nameText}`)
      .then((response) => response.json())
      .then((jsonRespone) => setSchedules(jsonRespone));
  };

  const fetchFilterSchedulesByStartingDate = async () => {
    fetch(`/alpine-octopus/api/v1/schedule/page/starting-date-filter/${formatStartingDate()}`)
      .then((response) => response.json())
      .then((jsonResponse) => setSchedules(jsonResponse));
  };

  // const fetchFilterSchedulesByPlannedTillDate = async () => {
  //   fetch(
  //     `/api/v1/schedule/page/planned-till-filter/${formatPlannedTillDate()}`
  //   )
  //     .then((response) => response.json())
  //     .then((jsonResponse) => setSchedules(jsonResponse));
  // };

  useEffect(() => {
    nameText.length > 0
      ? fetchFilterSchedulesByName()
      : startingDate === "Invalid Date"
      ? fetchSchedules()
       // .then(setStartingDate(""))
      : startingDate.length > 0
      ? fetchFilterSchedulesByStartingDate()
      : fetchSchedules();
  }, [activePage, nameText, startingDate]);

  // useEffect(() => {
  //   nameText.length > 0
  //     ? setStartingDate("").then(setPlannedTillDate("")).then(fetchFilterSchedulesByName())
  //     : startingDate === "Invalid Date" || plannedTillDate === "Invalid Date"
  //     ? setStartingDate("").then(setPlannedTillDate("")).then(fetchSchedules())
  //     : startingDate.length > 0
  //     ? setNameText("").then(setPlannedTillDate("")).then(fetchFilterSchedulesByStartingDate())
  //     : plannedTillDate.length > 0
  //     ? setNameText("").then(setStartingDate("")).then(fetchFilterSchedulesByPlannedTillDate())
  //     : fetchSchedules();
  // }, [activePage, nameText, startingDate, plannedTillDate]);

  // useEffect(() => {
  //   if (
  //     (nameText.length === 0 &&
  //       startingDate.length === 0 &&
  //       plannedTillDate.length === 0)
  //   ) {
  //     fetchSchedules();
  //   } else if (nameText.length > 0 ) {
  //     setNameText("");
  //     setStartingDate("");
  //     fetchFilterSchedulesByName();
  //   } else if (startingDate > 0 ) {
  //     setNameText("");
  //     setPlannedTillDate("");
  //     fetchFilterSchedulesByStartingDate();
  //   } else if (plannedTillDate.length > 0) {
  //     setNameText("");
  //     setStartingDate("");
  //     fetchFilterSchedulesByPlannedTillDate();
  //   }
  // }, [activePage, nameText, startingDate, plannedTillDate]);

  const formatStartingDate = () => {
    return startingDate === "" ? "" : dayjs(startingDate).format("YYYY-MM-DD");
  };

  // const formatPlannedTillDate = () => {
  //   return plannedTillDate === ""
  //     ? ""
  //     : dayjs(plannedTillDate).format("YYYY-MM-DD");
  // };

  return (
    <div>
      {/* <ButtonSizes/> */}
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

              <DatePicker
                className="controls4"
                placeholder="Filtruoti nuo"
                onChange={(e) => {
                  const newDate = dayjs(e).format("YYYY-MM-DD");
                  setStartingDate(newDate);
                }}
              />

              {/* <DatePicker
                className="controls4"
                placeholder="Filtruoti iki"
                onChange={(e) => {
                  const newDate = dayjs(e).format("YYYY-MM-DD");
                  setPlannedTillDate(newDate);
                }}
              /> */}
              {/* <RangePicker
                className="controls5"
                onChange={(values) => {
                  // const start = dayjs(values[0]).format("YYYY-MM-DD");
                  // const end = dayjs(values[1]).format("YYYY-MM-DD");
                  setDates(
                    values.map((item) => {
                      return dayjs(item).format("YYYY-MM-DD");
                    })
                  );
                  setStartingDate(dates[0]);
                  setPlannedTillDate(dates[1]);
                }}
              /> */}

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
                      <Table.Cell>{schedule.startingDate}</Table.Cell>
                      <Table.Cell>{schedule.plannedTillDate}</Table.Cell>
                      <Table.Cell collapsing>
                        {schedule.status === "invalid" ? (
                          <Button
                            id="grey"
                            basic
                            compact
                            icon="clock outline"
                            title="Negaliojantis"
                          />
                        ) : schedule.status === "Valid" ? (
                          <Button
                            id="okey"
                            basic
                            compact
                            icon="check"
                            title="Galiojantis"
                          />
                        ) : (
                          <Button
                            id="attention"
                            basic
                            compact
                            icon="attention"
                            title="Nevaliduotas"
                          />
                        )}
                      </Table.Cell>
                      <Table.Cell collapsing>
                        {" "}
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
