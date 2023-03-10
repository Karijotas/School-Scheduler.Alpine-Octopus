import React, { useEffect, useState } from "react";
import { NavLink } from "react-router-dom";
import dayjs from "dayjs";
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
  TextArea,
} from "semantic-ui-react";
import moment from "moment";
import MainMenu from "../../Components/MainMenu";
import { SchedulesMenu } from "../../Components/SchedulesMenu";
import { format } from "date-fns";
import { DatePicker } from "antd";
import "antd/dist/reset.css";
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

  const fetchSchedules = async () => {
    fetch("/api/v1/schedule/page?page=" + activePage)
      .then((response) => response.json())
      .then((jsonResponse) => setSchedules(jsonResponse));
  };

  const removeSchedule = (id) => {
    fetch("/api/v1/schedule/delete/" + id, {
      method: "PATCH",
    })
      .then(fetchSchedules)
      .then(setOpen(false));
  };

  const fetchFilterSchedulesByName = async () => {
    fetch(`/api/v1/schedule/page/name-filter/${nameText}`)
      .then((response) => response.json())
      .then((jsonRespone) => setSchedules(jsonRespone));
  };

  const fetchFilterSchedulesByStartingDate = async () => {
    fetch(`/api/v1/schedule/page/starting-date-filter/${formatStartingDate()}`)
      .then((response) => response.json())
      .then((jsonResponse) => setSchedules(jsonResponse))
      ;
  };

  const fetchFilterSchedulesByPlannedTillDate = async () => {
    fetch(`/api/v1/schedule/page/planned-till-filter/${formatPlannedTillDate()}`)
      .then((response) => response.json())
      .then((jsonResponse) => setSchedules(jsonResponse))
      ;
  };

  useEffect(() => {
    nameText.length > 0
      ? fetchFilterSchedulesByName()
      : startingDate === "Invalid Date" || plannedTillDate === "Invalid Date"
      ? fetchSchedules().then(setStartingDate("")).then(setPlannedTillDate(""))
      : startingDate.length > 0  
      ? fetchFilterSchedulesByStartingDate()
      : plannedTillDate.length > 0
      ? fetchFilterSchedulesByPlannedTillDate()
      : fetchSchedules();
  }, [activePage, nameText, startingDate, plannedTillDate]);

  const formatStartingDate = () => {
    return startingDate === "" ? "" : dayjs(startingDate).format("YYYY-MM-DD");
  };

  const formatPlannedTillDate = () => {
    return plannedTillDate === ""? "": dayjs(plannedTillDate).format("YYYY-MM-DD");
  };

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
                title="Filtruoti pagal pavadinim??"
                className="controls1"
                placeholder="Filtruoti pagal pavadinim??"
                value={nameText}
                onChange={(e) => setNameText(e.target.value)}
              />

              {/* <Input
                className="controls1"
                title="Filtruoti nuo"
                placeholder="Filtruoti nuo"
                value={startingDate}
                onChange={(e) => setStartingDate(e.target.value)}
                required={plannedTillDate}
              /> */}
              <DatePicker
                className="controls4"
                placeholder="Filtruoti nuo"
                onChange={(e) => {
                  const newDate = dayjs(e).format("YYYY-MM-DD");
                  setStartingDate(newDate);
                }}
              />

              <DatePicker
                className="controls4"
                placeholder="Filtruoti iki"
                onChange={(e) => {
                  const newDate = dayjs(e).format("YYYY-MM-DD");
                  setPlannedTillDate(newDate);
                }}
              />

              {/* <Input
                className="controls1"
                title="Filtruoti iki"
                placeholder="Filtruoti iki"
                value={plannedTillDate}
                onChange={(e) => setPlannedTillDate(e.target.value)}
              /> */}
              <Divider horizontal hidden></Divider>

              <Table selectable>
                <Table.Header>
                  <Table.Row>
                    <Table.HeaderCell>
                      Grup??s/ tvarkara????io pavadinimas
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
                            title="Statusas"
                          />
                        ) : schedule.status === "Valid" ? (
                          <Button
                            id="okey"
                            basic
                            compact
                            icon="check"
                            title="Statusas"
                          />
                        ) : (
                          <Button
                            id="attention"
                            basic
                            compact
                            icon="attention"
                            title="Statusas"
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
                          title="Per??i??r??ti"
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
                          header="D??mesio!"
                          content="Ar tikrai norite perkelti ?? archyv???"
                          cancelButton="Gr????ti atgal"
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
