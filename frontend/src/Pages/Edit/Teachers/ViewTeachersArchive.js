import React, { useEffect, useState } from "react";
import {
  Button,
  ButtonGroup, Divider, Grid, Icon, Segment,
  Table
} from "semantic-ui-react";
import { EditMenu } from '../../../Components/EditMenu';
import MainMenu from "../../../Components/MainMenu";

const JSON_HEADERS = {
  "Content-Type": "application/json",
};

export function ViewTeachersArchive() {

  const [teachers, setTeachers] = useState([]);
  const [teachersforPaging, setTeachersForPaging] = useState([]);
  const [activePage, setActivePage] = useState(0);
  const [pagecount, setPageCount] = useState();

  const fetchSingleTeachers = async () => {
    fetch('/alpine-octopus/api/v1/teachers/archive/')
      .then((response) => response.json())
      .then((jsonResponse) => setTeachersForPaging(jsonResponse))
      .then(setPageCount(Math.ceil(teachersforPaging.length / 10)));
  };

  const fetchPagedTeachers = async () => {
    fetch('/alpine-octopus/api/v1/teachers/archive/page?page=' + activePage)
      .then((response) => response.json())
      .then((jsonResponse) => setTeachers(jsonResponse));
  };

  const fetchTeachers = async () => {
    fetch(`/alpine-octopus/api/v1/teachers/archive/`)
      .then((response) => response.json())
      .then((jsonRespones) => setTeachers(jsonRespones));
  };

  useEffect(() => {
    fetch("/alpine-octopus/api/v1/teachers/archive/page?page=" + activePage)
      .then((response) => response.json())
      .then((jsonRespones) => setTeachers(jsonRespones));
  }, []);

  const restoreTeacher = (id) => {
    fetch("/alpine-octopus/api/v1/teachers/restore/" + id, {
      method: "PATCH",
    }).then(fetchPagedTeachers);
  };

  useEffect(() => {
    if (pagecount !== null) {
      fetchSingleTeachers();
    }
  }, [teachers]);

  useEffect(() => {
    fetchPagedTeachers();
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
            <div id="teachers">
              <Table selectable>
                <Table.Header>
                  <Table.Row>
                    <Table.HeaderCell>
                      Archyvas. Mokytojo vardas
                    </Table.HeaderCell>
                    <Table.HeaderCell collapsing textAlign="center">
                      Veiksmai
                    </Table.HeaderCell>
                  </Table.Row>
                </Table.Header>
                <Table.Body>
                  {teachers.map((teacher) => (
                    <Table.Row key={teacher.id}>
                      <Table.Cell disabled>{teacher.name}</Table.Cell>
                      <Table.Cell>
                        <Button
                          textAlign="center"
                          basic
                          compact
                          title="Atstatyti"
                          icon="undo"
                          onClick={() => restoreTeacher(teacher.id)}
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
