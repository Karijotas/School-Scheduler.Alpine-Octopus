import React, { useEffect, useState } from "react";
import {
  Button,
  ButtonGroup, Divider, Grid, Icon,
  Input,
  Segment,
  Table
} from "semantic-ui-react";
import { EditMenu } from '../../../Components/EditMenu';
import MainMenu from "../../../Components/MainMenu";

const JSON_HEADERS = {
  "Content-Type": "application/json",
};

export function ViewShiftsArchive() {
  const [shifts, setShifts] = useState([]);
  const [shiftsforPaging, setShiftsForPaging] = useState([]);
  const [activePage, setActivePage] = useState(0);
  const [pagecount, setPageCount] = useState();

  const fetchSingleShifts = async () => {
    fetch('/alpine/api/v1/shifts/archive/')
      .then((response) => response.json())
      .then((jsonResponse) => setShiftsForPaging(jsonResponse))
      .then(setPageCount(Math.ceil(shiftsforPaging.length / 10)));
  };

  const fetchPagedShifts = async () => {
    fetch('/alpine/api/v1/shifts/archive/page?page=' + activePage)
      .then((response) => response.json())
      .then((jsonResponse) => setShifts(jsonResponse));
  };

  const fetchShifts = async () => {
    fetch(`/alpine/api/v1/shifts/archive/`)
      .then((response) => response.json())
      .then((jsonRespones) => setShifts(jsonRespones));
  };

  useEffect(() => {
    fetch("/alpine/api/v1/shifts/archive/page?page=" + activePage)
      .then((response) => response.json())
      .then((jsonRespones) => setShifts(jsonRespones));
  }, []);

  const restoreShift = (id) => {
    fetch("/alpine/api/v1/shifts/restore/" + id, {
      method: "PATCH",
    }).then(fetchPagedShifts);
  };

  useEffect(() => {
    if (pagecount !== null) {
      fetchSingleShifts();
    }
  }, [shifts]);

  useEffect(() => {
    fetchPagedShifts();
  }, [activePage]);

  return (
    <div>
      <MainMenu />

      <Grid columns={2}>
        <Grid.Column width={2} id="main">
          <EditMenu />
        </Grid.Column>
        <Grid.Column textAlign="left" verticalAlign="top" width={13}>
          <Segment id="segment" raised color="grey">
            <div id="shifts">
              <Table selectable>
                <Table.Header>
                  <Table.Row>
                    <Table.HeaderCell>
                      Archyvas. Pamainos pavadinimas
                    </Table.HeaderCell>
                    <Table.HeaderCell collapsing textAlign="center">
                      Veiksmai
                    </Table.HeaderCell>
                  </Table.Row>
                </Table.Header>
                <Table.Body>
                  {shifts.map((shift) => (
                    <Table.Row key={shift.id}>
                      <Table.Cell disabled>{shift.name}</Table.Cell>
                      <Table.Cell>
                        <Button
                          textAlign="center"
                          basic                          
                          compact
                          title="Atstatyti"
                          icon="undo"
                          onClick={() => restoreShift(shift.id)}
                        ></Button>
                      </Table.Cell>
                    </Table.Row>
                  ))}
                </Table.Body>
              </Table>
              <Divider hidden></Divider>

              <ButtonGroup compact basic>
                                <Button title='Atgal' onClick={() => setActivePage(activePage <= 0 ? activePage : activePage - 1)} icon><Icon name="arrow left" />  </Button>
                                {/* {[...Array(pagecount)].map((e, i) => {
                                    return <Button title={i + 1} key={i} active={activePage === i ? true : false} onClick={() => setActivePage(i)}>{i + 1}</Button>
                                })} */}
                                <Button title='Pirmyn' onClick={() => setActivePage(activePage >= pagecount - 1 ? activePage : activePage + 1)} icon><Icon name="arrow right" />  </Button>
                            </ButtonGroup>
            </div>
          </Segment>
        </Grid.Column>
      </Grid>
    </div>
  );
}
