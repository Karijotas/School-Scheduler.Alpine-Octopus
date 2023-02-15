import React, { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import {
  Button,
  ButtonGroup,
  Divider,
  Dropdown,
  Icon,
  Input,
  Pagination,
  Table,
} from "semantic-ui-react";
import { CreateProgramPage } from '../../Create/CreateProgramPage';
import { EditProgramObject } from './EditProgramObject';
import './ViewGroups.css';


const JSON_HEADERS = {
  "Content-Type": "application/json",
};

export function ViewPrograms() {

  const [active, setActive] = useState()
  const [create, setCreate] = useState('')
  const [nameText, setNameText] = useState("");
  const [programs, setPrograms] = useState([]);
  const [groupsforPaging, setGroupsForPaging] = useState([]);

  const [activePage, setActivePage] = useState(0);
  const [pagecount, setPageCount] = useState()



  const fetchFilterPrograms = async () => {
    fetch(`/api/v1/programs/page/starting-with/${nameText}?page=` + activePage)
      .then((response) => response.json())
      .then((jsonRespone) => setPrograms(jsonRespone));
  };

  const fetchSinglePrograms = () => {
    fetch('/api/v1/programs')
      .then(response => response.json())
      .then(jsonResponse => setGroupsForPaging(jsonResponse)).then(setPageCount(Math.ceil(groupsforPaging.length / 10)))
  };


  const fetchPrograms = async () => {
    fetch(`/api/v1/programs/page?page=` + activePage)
      .then((response) => response.json())
      .then((jsonRespones) => setPrograms(jsonRespones));
  };

  const removeProgram = (id) => {
    fetch("/api/v1/programs/" + id, {
      method: "DELETE",
      headers: JSON_HEADERS,
    }).then(fetchPrograms);
  };


  useEffect(() => {
    nameText.length > 0 ? fetchFilterPrograms() : fetchPrograms();
  }, [activePage, nameText]);

  const [open, setOpen] = useState(false)
  const [close, setClose] = useState(false)


  useEffect(() => {
    if (pagecount !== null) {
      fetchSinglePrograms();
    }
  }, [programs])

  return (
    <div>
      {create && (<div>
        <CreateProgramPage /></div>)}
      {active && (<div className='edit'>
        <EditProgramObject id={active} /></div>)}

      {!active && !create && (
        <div id="programs">
          <Input
            value={nameText}
            onChange={(e) => setNameText(e.target.value)}
            placeholder="Ieškoti pagal pavadinimą"
          />
          {/* <Button onClick={fetchFilterPrograms}>Filtruoti</Button> */}


          <Button icon labelPosition='left' primary className='controls'><Icon name='database' />Kurti naują programą</Button>
          <Divider horizontal hidden></Divider>

          <Table selectable>
            <Table.Header>
              <Table.Row>
                <Table.HeaderCell>Programos pavadinimas</Table.HeaderCell>
                <Table.HeaderCell>Programos aprašymas</Table.HeaderCell>
                <Table.HeaderCell>Paskutinis atnaujinimas:</Table.HeaderCell>
                <Table.HeaderCell>Veiksmai</Table.HeaderCell>
              </Table.Row>
            </Table.Header>


            <Table.Body>
              {programs.map((program) => (
                <Table.Row key={program.id}>
                  <Table.Cell>{program.name}</Table.Cell>
                  <Table.Cell>{program.description}</Table.Cell>
                  <Table.Cell>{program.modifiedDate}</Table.Cell>
                  <Table.Cell collapsing>
                    <Button
                      basic
                      primary
                      compact
                      icon="eye"
                      title="Peržiūrėti"
                      onClick={() => setActive(program.id)}
                    ></Button>
                    <Button
                      basic
                      color="black"
                      compact
                      icon="trash alternate"
                      onClick={() => removeProgram(program.id)}
                    ></Button>
                  </Table.Cell>
                </Table.Row>
              ))}
            </Table.Body>
          </Table>
          <ButtonGroup basic compact>
            <Button onClick={() => setActivePage(activePage <= 0 ? activePage : activePage - 1)} icon><Icon name="arrow left" />  </Button>
            {[...Array(pagecount)].map((e, i) => {
              return <Button key={i} onClick={() => setActivePage(i)}>{(i + 1)}</Button>
            })}
            <Button onClick={() => setActivePage(activePage + 1)} icon><Icon name="arrow right" />  </Button>
          </ButtonGroup>
        </div>
      )}
    </div>
  );
}
