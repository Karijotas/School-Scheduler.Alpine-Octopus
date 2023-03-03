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

export function ViewRoomsArchive() {

  const [rooms, setRooms] = useState([]);
  const [roomsforPaging, setRoomsForPaging] = useState([]);
  const [activePage, setActivePage] = useState(0);
  const [pagecount, setPageCount] = useState();


  const fetchSingleRooms = () => {
    fetch("/api/v1/rooms/archive/")
      .then((response) => response.json())
      .then((jsonResponse) => setRoomsForPaging(jsonResponse))
      .then(setPageCount(Math.ceil(roomsforPaging.length / 10)));
  };

  const fetchPagedRooms = async () => {
    fetch('/api/v1/rooms/archive/page?page=' + activePage)
      .then((response) => response.json())
      .then((jsonResponse) => setRooms(jsonResponse));
  };

  const fetchRooms = async () => {
    fetch(`/api/v1/rooms/archive/`)
      .then((response) => response.json())
      .then((jsonRespones) => setRooms(jsonRespones));
  };

  useEffect(() => {
    fetch("/api/v1/rooms/archive/page?page=" + activePage)
      .then((response) => response.json())
      .then((jsonRespones) => setRooms(jsonRespones));
  }, []);

  useEffect(() => {
    fetchPagedRooms();
  }, [activePage]);

  const restoreRoom = (id) => {
    fetch("/api/v1/rooms/restore/" + id, {
      method: "PATCH",
    }).then(fetchPagedRooms);
  };

  useEffect(() => {
    if (pagecount !== null) {
      fetchSingleRooms();
    }
  }, [rooms]);

  return (
    <div>
      <MainMenu />

      <Grid columns={2} >
        <Grid.Column width={2} id='main'>
          <EditMenu />
        </Grid.Column>

        <Grid.Column textAlign='left' verticalAlign='top' width={13}>
          <Segment id='segment' raised color='grey'>

           <div id="rooms">
              {/* <Input
                value={nameText}
                onChange={(e) => setNameText(e.target.value)}
                placeholder="Ieškoti pagal modulį"
              /> */} 
              {/* <Button onClick={fetchFilterPrograms}>Filtruoti</Button> */}

              
              <Table selectable>
                <Table.Header>
                  <Table.Row>
                    <Table.HeaderCell>Archyvas. Klasės pavadinimas</Table.HeaderCell>

                    <Table.HeaderCell>Veiksmai</Table.HeaderCell>
                  </Table.Row>
                </Table.Header>

                <Table.Body>
                  {rooms.map((room) => (
                    <Table.Row key={room.id}>
                      <Table.Cell disabled>{room.name}</Table.Cell>

                      <Table.Cell collapsing>                     
                        <Button
                          basic                          
                          compact
                          title="Atstatyti"
                          icon="undo"
                          onClick={() => restoreRoom(room.id)}
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
