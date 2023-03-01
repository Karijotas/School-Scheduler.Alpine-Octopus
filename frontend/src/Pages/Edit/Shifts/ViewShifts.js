import React, { useEffect, useState } from "react";
import { NavLink } from "react-router-dom";
import {
  Button, Confirm, Divider,
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
    fetch(`/scheduler/api/v1/shifts/page/name-filter/${nameText}?page=` + activePage)
      .then((response) => response.json())
      .then((jsonRespone) => setShifts(jsonRespone));
  };

  const fetchSingleShifts = () => {
    fetch("/scheduler/api/v1/shifts")
      .then((response) => response.json())
      .then((jsonResponse) => setShiftsForPaging(jsonResponse))
      .then(setPageCount(Math.ceil(shiftsforPaging.length / 10)));
  };

  const fetchShifts = async () => {
    fetch(`/scheduler/api/v1/shifts/page?page=` + activePage)
      .then((response) => response.json())
      .then((jsonRespones) => setShifts(jsonRespones));
  };

  const removeShift = (id) => {
    fetch("/scheduler/api/v1/shifts/" + id, {
      method: "DELETE",
      headers: JSON_HEADERS,
    }).then(fetchShifts);
  };

  useEffect(() => {
    nameText.length > 0 ? fetchFilterShifts() : fetchShifts();
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
                  primary
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
                      <Table.HeaderCell>Pavadinimas</Table.HeaderCell>
                      <Table.HeaderCell>Pamokos nuo:</Table.HeaderCell>
                      <Table.HeaderCell>Pamokos iki:</Table.HeaderCell>
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
                            title="Ištrinti"
                            icon="trash alternate"
                            onClick={() => setOpen(shift.id)}
                          ></Button>

                          <Confirm
                            open={open}
                            header="Dėmesio!"
                            content="Ar tikrai norite ištrinti?"
                            cancelButton="Grįžti atgal"
                            confirmButton="Ištrinti"
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
          </Segment>
        </Grid.Column>
      </Grid>
    </div>
  );
}
