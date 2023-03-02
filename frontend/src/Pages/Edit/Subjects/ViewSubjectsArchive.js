import React, { useEffect, useState } from "react";
import {
  Button,
  ButtonGroup, Divider, Grid, Icon,
  Segment,
  Table
} from "semantic-ui-react";
import { EditMenu } from '../../../Components/EditMenu';
import MainMenu from "../../../Components/MainMenu";

const JSON_HEADERS = {
  "Content-Type": "application/json",
};

export function ViewSubjectsArchive() {

  const [subjects, setSubjects] = useState([]);
  const [subjectsForPaging, setSubjectsForPaging] = useState([]);
  const [activePage, setActivePage] = useState(0);
  const [pagecount, setPageCount] = useState();

  const fetchSingleSubject = async () => {
    fetch('/api/v1/subjects/archive/')
      .then((response) => response.json())
      .then((jsonResponse) => setSubjectsForPaging(jsonResponse))
      .then(setPageCount(Math.ceil(subjectsForPaging.length / 10)));
  };

  const fetchPagedSubjects = async () => {
    fetch('/api/v1/subjects/archive/page?page=' + activePage)
      .then((response) => response.json())
      .then((jsonResponse) => setSubjects(jsonResponse));
  };

  const fetchSubjects = async () => {
    fetch(`/api/v1/subjects/archive/`)
      .then((response) => response.json())
      .then((jsonRespones) => setSubjects(jsonRespones));
  };

  useEffect(() => {
    fetch("/api/v1/subjects/archive/page?page=" + activePage)
      .then((response) => response.json())
      .then((jsonRespones) => setSubjects(jsonRespones));
  }, []);

  const restoreSubject = (id) => {
    fetch("/api/v1/subjects/restore/" + id, {
      method: "PATCH",
    }).then(fetchPagedSubjects);
  };

  useEffect(() => {
    if (pagecount !== null) {
      fetchSingleSubject();
    }
  }, [subjects]);

  useEffect(() => {
    fetchPagedSubjects();
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
            <div id="subjects">
              <Table selectable>
                <Table.Header>
                  <Table.Row>
                    <Table.HeaderCell>
                      Archyvas. Dalyko pavadinimas
                    </Table.HeaderCell>
                    <Table.HeaderCell collapsing textAlign="center">
                      Veiksmai
                    </Table.HeaderCell>
                  </Table.Row>
                </Table.Header>
                <Table.Body>
                  {subjects.map((subject) => (
                    <Table.Row key={subject.id}>
                      <Table.Cell disabled>{subject.name}</Table.Cell>
                      <Table.Cell>
                        <Button
                          textAlign="center"
                          basic                          
                          compact
                          title="Atstatyti"
                          icon="undo"
                          onClick={() => restoreSubject(subject.id)}
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
