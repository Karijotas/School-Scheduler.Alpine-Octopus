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

export function ViewGroupsArchive() {

  const [active, setActive] = useState();
  const [groups, setGroups] = useState([]);
  const [groupsforPaging, setGroupsForPaging] = useState([]);
  const [activePage, setActivePage] = useState(0);
  const [pagecount, setPageCount] = useState();

  const fetchSingleGroups = async () => {
    fetch('/api/v1/groups/archive/')
      .then((response) => response.json())
      .then((jsonResponse) => setGroupsForPaging(jsonResponse))
      .then(setPageCount(Math.ceil(groupsforPaging.length / 10)));
  };

  const fetchPagedGroups = async () => {
    fetch('/api/v1/groups/archive/page?page=' + activePage)
      .then((response) => response.json())
      .then((jsonResponse) => setGroups(jsonResponse));
  };

  const fetchGroups = async () => {
    fetch(`/api/v1/groups/archive/`)
      .then((response) => response.json())
      .then((jsonRespones) => setGroups(jsonRespones));
  };

  useEffect(() => {
    fetch("/api/v1/groups/archive/page?page=" + activePage)
      .then((response) => response.json())
      .then((jsonRespones) => setGroups(jsonRespones));
  }, []);

  const restoreGroup = (id) => {
    fetch("/api/v1/groups/restore/" + id, {
      method: "PATCH",
    }).then(fetchPagedGroups);
  };

  useEffect(() => {
    if (pagecount !== null) {
      fetchSingleGroups();
    }
  }, [groups]);

  useEffect(() => {
    fetchPagedGroups();
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
            <div id="groups">
              <Table selectable>
                <Table.Header>
                  <Table.Row>
                    <Table.HeaderCell>
                      Archyvas. Grupės pavadinimas
                    </Table.HeaderCell>
                    <Table.HeaderCell collapsing textAlign="center">
                      Veiksmai
                    </Table.HeaderCell>
                  </Table.Row>
                </Table.Header>
                <Table.Body>
                  {groups.map((group) => (
                    <Table.Row key={group.id}>
                      <Table.Cell disabled>{group.name}</Table.Cell>
                      <Table.Cell collapsing>
                      <Button                        
                          href={"#/view/archives/groups/" + group.id}
                          basic
                          compact
                          icon="eye"
                          title="Peržiūrėti"
                          onClick={() => setActive(group.id)}
                        ></Button>
                        <Button
                          textAlign="center"
                          basic
                          compact
                          title="Atstatyti"
                          icon="undo"
                          onClick={() => restoreGroup(group.id)}
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
