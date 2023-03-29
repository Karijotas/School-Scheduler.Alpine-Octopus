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
  Segment,
  Table
} from "semantic-ui-react";
import { EditMenu } from "../../../Components/EditMenu";
import MainMenu from "../../../Components/MainMenu";
import { DatePicker} from "antd";
import dayjs from "dayjs";

const JSON_HEADERS = {
    "Content-Type": "application/json",
  };

  export function ViewHolidays() {

const [holidays,setHolidays]= useState([]);
const [active, setActive] = useState("");;
const [activePage, setActivePage] = useState(0);
const [nameText, setNameText] = useState("");
const [pagecount, setPageCount] = useState();
const [holidaysforPaging, setHolidaysForPaging] = useState([]);
const [open, setOpen] = useState(false);

const { RangePicker } = DatePicker;
const [startDate, setStartDate] = useState(null);
const [endDate, setEndDate] = useState(null);

const fetchHolidays = async () => {
  fetch(`/api/v1/holidays/page?page=` + activePage)
    .then((response) => response.json())
    .then((jsonResponse) => setHolidays(jsonResponse));  
};

const encodedNameText = encodeURIComponent(nameText);

const fetchFilterHolidays = async () => {
  fetch(`/api/v1/holidays/name-filter/${encodedNameText}`)
    .then((response) => response.json())
    .then((jsonResponse) => setHolidays(jsonResponse));
};

const fetchDateFilterHolidays = async () => {
  if (startDate && endDate) {
    fetch("/api/v1/holidays/date-range-filter/?startDate=" + startDate + "&endDate=" + endDate)
    .then((response) => response.json())
    .then((jsonResponse) => setHolidays(jsonResponse));
  }
}

const handleDateChange = (dates) => {
  if (dates && dates.length === 2) {
    setStartDate(dayjs(dates[0]).format("YYYY-MM-DD"));
    setEndDate(dayjs(dates[1]).format("YYYY-MM-DD"));
  } else {
    setStartDate(null);
    setEndDate(null);
  }
};

const removeHoliday = (id) => {
    fetch("/api/v1/holidays/" + id, {
        method: "DELETE",
    })
      .then(fetchHolidays)
      .then(setOpen(false));
  };

  const fetchSingleHolidays = async () => {
    fetch("/api/v1/holidays/")
      .then((response) => response.json())
      .then((jsonResponse) => setHolidaysForPaging(jsonResponse))
      .then(setPageCount(Math.ceil(holidaysforPaging.length / 10)));
  };

  useEffect(() => {
    if (pagecount !== null) {
      fetchSingleHolidays();
    }
  }, [holidays]);


  useEffect(() => {
    if (nameText.length === 0) {
      fetchHolidays();
    } else {
      if(!startDate === null && !endDate === null){
        fetchDateFilterHolidays();
      }else{
         fetchFilterHolidays();
      }
       
    }
  }, [activePage, nameText,startDate,endDate]);

 
  return (
    <div>
      <MainMenu />

      <Grid columns={2}>
        <Grid.Column width={2} id="main">
          <EditMenu />
        </Grid.Column>

        <Grid.Column textAlign="left" verticalAlign="top" width={13}>
          <Segment id="segment" color="teal">
            <div id="holidays">
              <Input
                className="controls1"
                value={nameText}
                onChange={(e) => setNameText(e.target.value)}
                placeholder="Filtruoti pagal pavadinimą"
              />

              <RangePicker
              onChange={handleDateChange}
              placeholder={["Pradžios data", "Pabaigos data"]}
              /><Button type="primary" onClick={fetchDateFilterHolidays}>
              Filtruoti
            </Button>

              <Button
                id="details"
                icon
                labelPosition="left"
                className="controls"
                as={NavLink}
                exact
                to="/create/holidays"
              >
                <Icon name="database" />
                Kurti naują
              </Button>
              <Divider horizontal hidden></Divider>
              <Table selectable>
                <Table.Header>
                  <Table.Row>
                    <Table.HeaderCell>Atostogų pavadinimas</Table.HeaderCell>
                    <Table.HeaderCell>Pradžios data</Table.HeaderCell>
                    <Table.HeaderCell>Pabaigos data</Table.HeaderCell>
                    <Table.HeaderCell>Veiksmai</Table.HeaderCell>
                  </Table.Row>
                </Table.Header>

                <Table.Body>
                  {holidays.map((holiday) => (
                    <Table.Row key={holiday.id}>
                      <Table.Cell>{holiday.name}</Table.Cell>
                      <Table.Cell>{holiday.startDate}</Table.Cell>
                      <Table.Cell>{holiday.endDate}</Table.Cell>

                      <Table.Cell collapsing>
                        <Button
                          id="icocolor"
                          href={"#/view/holidays/edit/" + holiday.id}
                          basic
                          compact
                          icon="eye"
                          title="Peržiūrėti"
                          onClick={() => setActive(holiday.id)}
                        ></Button>
                        <Button
                          id="icocolor"
                          basic
                          compact
                          title="Ištrinti"
                          icon="trash alternate"
                          onClick={() => setOpen(holiday.id)}
                        ></Button>
                        <Confirm
                          open={open}
                          header="Dėmesio!"
                          content="Ar tikrai norite Ištrinti?"
                          cancelButton="Grįžti atgal"
                          confirmButton="Taip"
                          onCancel={() => setOpen(false)}
                          onConfirm={() => removeHoliday(open)}
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