import React, { useEffect, useState } from "react";
import { NavLink } from 'react-router-dom';
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
import { EditMenu } from '../../../Components/EditMenu';
import MainMenu from '../../../Components/MainMenu';

const JSON_HEADERS = {
  "Content-Type": "application/json",
};

export function ViewModules() {
  const [active, setActive] = useState();
  const [create, setCreate] = useState("");
  const [nameText, setNameText] = useState("");
  const [modules, setModules] = useState([]);

  const [modulesforPaging, setModulesForPaging] = useState([]);

  const [activePage, setActivePage] = useState(0);
  const [pagecount, setPageCount] = useState();

  const fetchFilterModules = async () => {
    fetch(`/scheduler/api/v1/modules/page/name-filter/${nameText}?page=` + activePage)
      .then((response) => response.json())
      .then((jsonRespone) => setModules(jsonRespone));

  };

  const fetchSingleModules = () => {
    fetch("/scheduler/api/v1/modules")
      .then((response) => response.json())
      .then((jsonResponse) => setModulesForPaging(jsonResponse))
      .then(setPageCount(Math.ceil(modulesforPaging.length / 10)));
  };

  const fetchModules = async () => {
    fetch(`/scheduler/api/v1/modules/page?page=` + activePage)
      .then((response) => response.json())
      .then((jsonRespones) => setModules(jsonRespones));
  };

  const removeModule = (id) => {
    fetch("/scheduler/api/v1/modules/" + id, {
      method: "DELETE",
      headers: JSON_HEADERS,
    }).then(fetchModules);
  };

  useEffect(() => {
    nameText.length > 0 ? fetchFilterModules() : fetchModules();
  }, [activePage, nameText]);

  const [open, setOpen] = useState(false);
  const [close, setClose] = useState(false);

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
          <Segment id='segment' color='teal'>
            {!active && !create && (
              <div id="modules">
                <Input
                  className="controls1"
                  placeholder="Filtruoti pagal pavadinimą"
                  value={nameText}
                  onChange={(e) => setNameText(e.target.value)}
                />

                <Button
                  icon
                  labelPosition="left"
                  primary
                  className="controls"
                  as={NavLink}
                  exact to='/create/modules'>
                  <Icon name="database" />
                  Kurti naują modulį
                </Button>
                <Divider horizontal hidden></Divider>

                <Table selectable>
                  <Table.Header>
                    <Table.Row>
                      <Table.HeaderCell>Modulio pavadinimas</Table.HeaderCell>
                      <Table.HeaderCell>Veiksmai</Table.HeaderCell>
                    </Table.Row>
                  </Table.Header>

                  <Table.Body>
                    {modules.map((module) => (
                      <Table.Row key={module.id}>
                        <Table.Cell>{module.name}</Table.Cell>
                        <Table.Cell collapsing>
                          <Button
                            href={'#/view/modules/edit/' + module.id}
                            basic
                            primary
                            compact
                            icon="eye"
                            title="Peržiūrėti"
                            onClick={() => setActive(module.id)}
                          ></Button>
                          <Button
                            basic
                            color="black"
                            compact
                            title="Ištrinti"
                            icon="trash alternate"
                            onClick={() => setOpen(module.id)}
                          ></Button>

                          <Confirm
                            open={open}
                            header="Dėmesio!"
                            content="Ar tikrai norite ištrinti?"
                            cancelButton="Grįžti atgal"
                            confirmButton="Ištrinti"
                            onCancel={() => setOpen(false)}
                            onConfirm={() => removeModule(open)}
                            size="small"
                          />
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
            )}
          </Segment>
        </Grid.Column>
      </Grid>
    </div>
  );
}
