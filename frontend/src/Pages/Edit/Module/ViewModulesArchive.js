import React, { useEffect, useState } from "react";
import { NavLink } from 'react-router-dom';
import {
  Button,
  ButtonGroup, Confirm, Divider, Grid, Icon,
  Input,
  Segment,
  Table
} from "semantic-ui-react";
import MainMenu from '../../../Components/MainMenu';
import { EditMenu } from '../../../Components/EditMenu';

const JSON_HEADERS = {
  "Content-Type": "application/json",
};

export function ViewModulesArchive() {
  const [active, setActive] = useState();
  const [create, setCreate] = useState("");
  const [nameText, setNameText] = useState("");
  const [programs, setPrograms] = useState([]);
  const [groupsforPaging, setGroupsForPaging] = useState([]);

  const [activePage, setActivePage] = useState(0);
  const [pagecount, setPageCount] = useState();

  const fetchFilterPrograms = async () => {
    fetch(`/api/v1/programs/page/starting-with/${nameText}?page=` + activePage)
      .then((response) => response.json())
      .then((jsonRespone) => setPrograms(jsonRespone));
  };

  const fetchSinglePrograms = () => {
    fetch("/api/v1/programs")
      .then((response) => response.json())
      .then((jsonResponse) => setGroupsForPaging(jsonResponse))
      .then(setPageCount(Math.ceil(groupsforPaging.length / 10)));
  };

  const fetchPrograms = async () => {
    fetch(`/api/v1/programs/archive/`)
      .then((response) => response.json())
      .then((jsonRespones) => setPrograms(jsonRespones));
  };

  const removeProgram = (id) => {
    fetch("/api/v1/programs/" + id, {
      method: "DELETE",
      headers: JSON_HEADERS,
    }).then(fetchPrograms)
      .then(setOpen(false));
  };

  useEffect(() => {
    nameText.length > 0 ? fetchFilterPrograms() : fetchPrograms();
  }, [activePage, nameText]);

  const [open, setOpen] = useState(false);
  const [close, setClose] = useState(false);


  useEffect(() => {
    if (pagecount !== null) {
      fetchSinglePrograms();
    }
  }, [programs]);

  return (
    <div>
      <MainMenu />

      <Grid columns={2} >
        <Grid.Column width={2} id='main'>
          <EditMenu />
        </Grid.Column>

        <Grid.Column textAlign='left' verticalAlign='top' width={13}>
          <Segment id='segment' raised color='grey'>

            <div id="programs">
              <Input
                value={nameText}
                onChange={(e) => setNameText(e.target.value)}
                placeholder="Ieškoti pagal programą"
              />
              {/* <Button onClick={fetchFilterPrograms}>Filtruoti</Button> */}

              
              <Table selectable>
                <Table.Header>
                  <Table.Row>
                    <Table.HeaderCell>Programos pavadinimas</Table.HeaderCell>

                    <Table.HeaderCell>Veiksmai</Table.HeaderCell>
                  </Table.Row>
                </Table.Header>

                <Table.Body>
                  {programs.map((program) => (
                    <Table.Row key={program.id}>
                      <Table.Cell>{program.name}</Table.Cell>

                      <Table.Cell collapsing>                     
                        <Button
                          basic
                          color="black"
                          compact
                          title="Atstatyti"
                          icon="undo"
                          onClick={() => setOpen(program.id)}
                        ></Button>
                      </Table.Cell>
                    </Table.Row>
                  ))}
                </Table.Body>
              </Table>
              <Divider hidden></Divider>

              <ButtonGroup compact basic>
                <Button title='Atgal' onClick={() => setActivePage(activePage <= 0 ? activePage : activePage - 1)} icon><Icon name="arrow left" />  </Button>
                {[...Array(pagecount)].map((e, i) => {
                  return <Button title={i + 1} key={i} active={activePage === i ? true : false} onClick={() => setActivePage(i)}>{i + 1}</Button>
                })}
                <Button title='Pirmyn' onClick={() => setActivePage(activePage >= pagecount - 1 ? activePage : activePage + 1)} icon><Icon name="arrow right" />  </Button>
              </ButtonGroup>
            </div>

          </Segment>
        </Grid.Column>

      </Grid>
    </div>
  );
}
