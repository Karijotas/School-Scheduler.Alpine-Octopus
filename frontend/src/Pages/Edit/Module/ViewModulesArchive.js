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
  
  const [modules, setModules] = useState([]);
  const [modulesforPaging, setModulesForPaging] = useState([]);
  const [activePage, setActivePage] = useState(0);
  const [pagecount, setPageCount] = useState();


  const fetchSingleModules = () => {
    fetch("/api/v1/modules/archive/")
      .then((response) => response.json())
      .then((jsonResponse) => setModulesForPaging(jsonResponse))
      .then(setPageCount(Math.ceil(modulesforPaging.length / 10)));
  };

  const fetchPagedModules = async () => {
    fetch('/api/v1/modules/archive/page?page=' + activePage)
      .then((response) => response.json())
      .then((jsonResponse) => setModules(jsonResponse));
  };

  const fetchModules = async () => {
    fetch(`/api/v1/modules/archive/`)
      .then((response) => response.json())
      .then((jsonRespones) => setModules(jsonRespones));
  };

  useEffect(() => {
    fetch("/api/v1/modules/archive/page?page=" + activePage)
      .then((response) => response.json())
      .then((jsonRespones) => setModules(jsonRespones));
  }, []);

  useEffect(() => {
    fetchPagedModules();
  }, [activePage]);

  const restoreModule = (id) => {
    fetch("/api/v1/modules/restore/" + id, {
      method: "PATCH",
    }).then(fetchPagedModules);
  };

  useEffect(() => {
    if (pagecount !== null) {
      fetchSingleModules();
    }
  }, [modules]);

  return (
    <div>
      <MainMenu />

      <Grid columns={2} >
        <Grid.Column width={2} id='main'>
          <EditMenu />
        </Grid.Column>

        <Grid.Column textAlign='left' verticalAlign='top' width={13}>
          <Segment id='segment' raised color='grey'>

           <div id="modules">
              {/* <Input
                value={nameText}
                onChange={(e) => setNameText(e.target.value)}
                placeholder="Ieškoti pagal modulį"
              /> */} 
              {/* <Button onClick={fetchFilterPrograms}>Filtruoti</Button> */}

              
              <Table selectable>
                <Table.Header>
                  <Table.Row>
                    <Table.HeaderCell>Archyvas. Modulio pavadinimas</Table.HeaderCell>

                    <Table.HeaderCell>Veiksmai</Table.HeaderCell>
                  </Table.Row>
                </Table.Header>

                <Table.Body>
                  {modules.map((module) => (
                    <Table.Row key={module.id}>
                      <Table.Cell disabled>{module.name}</Table.Cell>

                      <Table.Cell collapsing>                     
                        <Button
                          basic                          
                          compact
                          title="Atstatyti"
                          icon="undo"
                          onClick={() => restoreModule(module.id)}
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
