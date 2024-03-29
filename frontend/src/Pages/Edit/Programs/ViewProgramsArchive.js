import React, { useEffect, useState } from "react";
import {
  Button,
  ButtonGroup,
  Divider,
  Grid,
  Icon,
  Segment,
  Table
} from "semantic-ui-react";
import { EditMenu } from '../../../Components/EditMenu';
import MainMenu from "../../../Components/MainMenu";

const JSON_HEADERS = {
  "Content-Type": "application/json",
};

export function ViewProgramsArchive() {

  const [active, setActive] = useState();
  const [programs, setPrograms] = useState([]);
  const [programsforPaging, setProgramsForPaging] = useState([]);
  const [activePage, setActivePage] = useState(0);
  const [pagecount, setPageCount] = useState();

  const fetchSinglePrograms = async () => {
    fetch('/api/v1/programs/archive/')
      .then((response) => response.json())
      .then((jsonResponse) => setProgramsForPaging(jsonResponse))
      .then(setPageCount(Math.ceil(programsforPaging.length / 10)));
  };

  const fetchPagedPrograms = async () => {
    fetch('/api/v1/programs/archive/page?page=' + activePage)
      .then((response) => response.json())
      .then((jsonResponse) => setPrograms(jsonResponse));
  };

  const fetchPrograms = async () => {
    fetch(`/api/v1/programs/archive/`)
      .then((response) => response.json())
      .then((jsonRespones) => setPrograms(jsonRespones));
  };

  useEffect(() => {
    fetch("/api/v1/programs/archive/page?page=" + activePage)
      .then((response) => response.json())
      .then((jsonRespones) => setPrograms(jsonRespones));
  }, []);

  const restoreProgram = (id) => {
    fetch("/api/v1/programs/restore/" + id, {
      method: "PATCH",
    }).then(fetchPagedPrograms);
  };

  useEffect(() => {
    if (pagecount !== null) {
      fetchSinglePrograms();
    }
  }, [programs]);

  useEffect(() => {
    fetchPagedPrograms();
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
            <div id="programs">
              <Table selectable>
                <Table.Header>
                  <Table.Row>
                    <Table.HeaderCell>
                      Archyvas. Programos pavadinimas
                    </Table.HeaderCell>
                    <Table.HeaderCell collapsing textAlign="center">
                      Veiksmai
                    </Table.HeaderCell>
                  </Table.Row>
                </Table.Header>
                <Table.Body>
                  {programs.map((program) => (
                    <Table.Row key={program.id}>
                      <Table.Cell disabled>{program.name}</Table.Cell>
                      <Table.Cell collapsing>
                      {/* <Button                          
                          href={"#/view/archives/programs/" + program.id}
                          basic
                          compact
                          icon="eye"
                          title="Peržiūrėti"
                          onClick={() => setActive(program.id)}
                        ></Button> */}
                        <Button
                          textAlign="center"
                          basic                          
                          compact
                          title="Atstatyti"
                          icon="undo"
                          onClick={() => restoreProgram(program.id)}
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
