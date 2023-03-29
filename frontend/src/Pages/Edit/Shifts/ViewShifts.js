import React, { useEffect, useState } from "react";
import { NavLink } from "react-router-dom";
import {
  Button, ButtonGroup, Confirm, Divider,
  Grid,
  Icon,
  Input,
  Segment,
  Table
} from "semantic-ui-react";
import { EditMenu } from "../../../Components/EditMenu";
import MainMenu from "../../../Components/MainMenu";
import { CreateShiftPage } from "./CreateShiftPage";

const JSON_HEADERS = {
  "Content-Type": "application/json",
};

export function ViewShifts() {
  const [active, setActive] = useState();
  const [create, setCreate] = useState("");
  const [nameText, setNameText] = useState("");
  const [shifts, setShifts] = useState([]);

  const [shiftsforPaging, setShiftsForPaging] = useState([]);

  const [activePage, setActivePage] = useState(0);
  const [pagecount, setPageCount] = useState();

  const fetchFilterShifts = async () => {
    fetch(`/alpine/api/v1/shifts/page/name-filter/${nameText}`)
      .then((response) => response.json())
      .then((jsonRespone) => setShifts(jsonRespone));
  };

  const fetchSingleShifts = () => {
    fetch("/alpine/api/v1/shifts")
      .then((response) => response.json())
      .then((jsonResponse) => setShiftsForPaging(jsonResponse))
      .then(setPageCount(Math.ceil(shiftsforPaging.length / 10)));
  };

  const fetchShifts = async () => {
    fetch(`/alpine/api/v1/shifts/page?page=` + activePage)
      .then((response) => response.json())
      .then((jsonRespones) => setShifts(jsonRespones));
  };

  const removeShift = (id) => {
    fetch("/alpine/api/v1/shifts/delete/" + id, {
      method: "PATCH",
    }).then(fetchShifts)
    .then(setOpen(false));
  };

  useEffect(() => {
    nameText.length > 0 && !nameText.includes('/') && !nameText.includes('#') && !nameText.includes('.') && !nameText.includes(';') && !nameText.match(new RegExp( /^\s/)) ? fetchFilterShifts() : fetchShifts();
  }, [activePage, nameText]);

  const [open, setOpen] = useState(false);
  const [close, setClose] = useState(false);

  useEffect(() => {
    if (pagecount !== null) {
      fetchSingleShifts();
    }
  }, [shifts]);

  return (
    <div>
      <MainMenu />
      <Grid columns={2}>
        <Grid.Column width={2} id="main">
          <EditMenu active="shifts" />
        </Grid.Column>

        <Grid.Column stretched textAlign="left" verticalAlign="top" width={13}>
          <Segment id="segment" raised color="teal">
            {create && (
              <div>
                <CreateShiftPage />
              </div>
            )}
            {!active && !create && (
              <div id="shifts">
                <Input
                  className="controls1"
                  placeholder="Filtruoti pagal pamainą"
                  value={nameText}
                  onChange={(e) => setNameText(e.target.value)}
                />

                <Button
                  icon
                  labelPosition="left"                  
                  className="controls"
                  as={NavLink}
                  exact
                  id='details'
                  to="/create/shifts"
                >
                  <Icon name="database" />
                  Kurti naują pamainą
                </Button>
                <Divider horizontal hidden></Divider>

                <Table selectable>
                  <Table.Header>
                    <Table.Row>
                      <Table.HeaderCell>Pamainos pavadinimas</Table.HeaderCell>
                      <Table.HeaderCell>Pamokos nuo</Table.HeaderCell>
                      <Table.HeaderCell>Pamokos iki</Table.HeaderCell>
                      <Table.HeaderCell>Veiksmai</Table.HeaderCell>
                    </Table.Row>
                  </Table.Header>
                  <Table.Body>
                    {shifts.map((shift) => (
                      <Table.Row key={shift.id} onChange={() => console.log(shift.id)}>
                        <Table.Cell>{shift.name}</Table.Cell>
                        <Table.Cell>{shift.starts}</Table.Cell>
                        <Table.Cell>{shift.ends}</Table.Cell>
                        <Table.Cell collapsing>
                        <Button
                        id="icocolor"
                            basic
                            compact
                            icon="eye"
                            title="Peržiūrėti"
                            href={"#/view/shifts/edit/" + shift.id}
                            onClick={() => setActive(shift.id)}
                          ></Button>
                          <Button
                          id="icocolor"
                            basic
                            compact
                            title="Suarchyvuoti"
                            icon="archive"
                            onClick={() => setOpen(shift.id)}
                          ></Button>

                          <Confirm
                            open={open}
                            header="Dėmesio!"
                            content="Ar tikrai norite perkelti į archyvą?"
                          cancelButton="Grįžti atgal"
                          confirmButton="Taip"
                            onCancel={() => setOpen(false)}
                            onConfirm={() => removeShift(open)}
                            size="small"
                          />
                        </Table.Cell>
                      </Table.Row>
                    ))}
                  </Table.Body>
                </Table>
              </div>
            )}
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
          </Segment>
        </Grid.Column>
      </Grid>
    </div>
  );
}
