import React, { useEffect, useState } from 'react'
import { json, Link } from 'react-router-dom';
import { Button,Divider, Confirm, Dropdown, Icon, Input, Pagination, Table } from 'semantic-ui-react'
import { CreateRoom } from '../../Create/CreateRoom';
import { EditRoom } from './EditRoom';



const JSON_HEADERS = {
    'Content-Type': 'application/json'
};


export function ViewRooms() {
    const [active, setActive] = useState('')

    const [create, setCreate] = useState('')

    const [rooms, setRooms] = useState([]);
//
    const [activePage, setActivePage] = useState(0);
    const [nameText, setNameText] = useState("");
    const [buildingText, setBuildingText] = useState("");

    

    const fetchRooms = async () => {
        fetch(`/api/v1/rooms/page?page=` + activePage)
            .then(response => response.json())
            .then(jsonResponse => setRooms(jsonResponse));
    };
    const fetchFilterRooms = async () => {
               fetch(`/api/v1/rooms/page/name-filter/${nameText}?page=` + activePage)
                 .then((response) => response.json())
                 .then((jsonRespone) => setRooms(jsonRespone));
             };
    
            
    const removeRoom = (id) => {
        fetch('/api/v1/rooms/' + id, {
            method: 'DELETE',
            headers: JSON_HEADERS
        }).then(fetchRooms).then(setOpen(false));
    }

    const fetchBuildingRooms = async () => {
        fetch(`/api/v1/rooms/page/building-filter/${buildingText}?page=` + activePage)
          .then((response) => response.json())
          .then((jsonRespone) => setRooms(jsonRespone));
      };


    useEffect(() => {
         //fetchRooms();
         if (nameText.length ===0 && buildingText.length === 0){
            fetchRooms();
         } else if (nameText.length > 0 && buildingText.length === 0){
            fetchFilterRooms();
         } else {
            fetchBuildingRooms();
         }
          //nameText.length > 0 ? fetchFilterRooms() : fetchRooms();
        //  buildingText.length > 0 ? fetchBuildingRooms() : fetchRooms();

             }, [activePage, nameText, buildingText]);

             const [open, setOpen] = useState(false)
            //  const [close, setClose] = useState(false)

    return (


        <div>
            {create && (<div><CreateRoom /></div>)}
                
            {active && (<div className='edit'><EditRoom id={active} /></div>)}
                
            {!active && !create && (

                <div id='rooms'>
                    <Input value={nameText} onChange={(e) => setNameText(e.target.value)} placeholder='Klasė' />
                    <Input value={buildingText} onChange={(e) => setBuildingText(e.target.value)} placeholder="Pastatas"/>

                    

                    <Button icon labelPosition='left' primary className='controls' onClick={() => setCreate('new')}><Icon name='database' />Kurti naują klasę</Button>
                    <Table selectable >
                        <Table.Header>
                            <Table.Row>
                                <Table.HeaderCell>Klasės pavadinimas</Table.HeaderCell>
                                <Table.HeaderCell>Pastatas</Table.HeaderCell>
                                <Table.HeaderCell>Aprašymas</Table.HeaderCell>
                                <Table.HeaderCell>Veiksmai</Table.HeaderCell>
                            </Table.Row>
                        </Table.Header>

                        <Table.Body>
                            {rooms.map(room => (

                                <Table.Row key={room.id}>
                                    <Table.Cell>{room.name}</Table.Cell>
                                    <Table.Cell>{room.building}</Table.Cell>
                                    <Table.Cell>{room.description}</Table.Cell>

                                    <Table.Cell collapsing>
                                        <Button basic primary compact icon='eye' title='Peržiūrėti' onClick={() => setActive(room.id)}></Button>
                                        <Button basic color='black' compact icon='trash alternate' onClick={() => setOpen(room.id)}></Button>
                                        <Confirm
                                            open={open}
                                            header='Dėmesio!'
                                            content='Ar tikrai norite ištrinti?'
                                            cancelButton='Grįžti atgal'
                                            confirmButton="Ištrinti"
                                            onCancel={() => setOpen(false)}
                                            onConfirm={() => removeRoom(open)}
                                            size='small'
                                        />

                                    </Table.Cell>
                                </Table.Row>
                            ))}
                        </Table.Body>

                        

                    </Table>
                    <Divider hidden></Divider>
                    <button onClick={()=> setActivePage(0)}> 1 </button>
                 <button onClick={()=> setActivePage(1)}> 2 </button> 
                 <button onClick={()=> setActivePage(2)}> 3 </button> 
                 <button onClick={()=> setActivePage(3)}> 4 </button>

                    {/* <Pagination
                        defaultActivePage={1}
                        firstItem={rooms.firstItem}
                        lastItem={rooms.lastItem}
                        pointing
                        totalPages={3}
                    /> */}
                </div>
            )}

        </div>
    )
}
// export function ViewRooms() {
//     const [activeItem, setActiveItem] = useState("");
//     const [nameText, setNameText] = useState("");  
//     const [rooms, setRooms] = useState([]);
//     const [activePage, setActivePage] = useState(0); 
  
//     const fetchFilterRooms = async () => {
//       fetch(`/api/v1/rooms/page/starting-with/${nameText}?page=` + activePage)
//         .then((response) => response.json())
//         .then((jsonRespone) => setRooms(jsonRespone));
//     };
  
//     const fetchRooms = async () => {
//       fetch(`/api/v1/rooms/page?page=` + activePage)
//         .then((response) => response.json())
//         .then((jsonRespones) => setRooms(jsonRespones));
//     };
  
//     const removeRoom = (id) => {
//       fetch("/api/v1/rooms/" + id, {
//         method: "DELETE",
//         headers: JSON_HEADERS,
//       }).then(fetchFilterRooms);
//     };
  
   
//     useEffect(() => {   
//       nameText.length > 0 ? fetchFilterRooms() : fetchRooms();
//     }, [activePage, nameText]);
  
//     return (
//       <div id="rooms">
//         <Input
//           value={nameText}
//           onChange={(e) => setNameText(e.target.value)}
//           placeholder="Ieškoti pagal pavadinimą"
//         />
//         {/* <Button onClick={fetchFilterRooms}>Filtruoti</Button> */}
  
//         <Button
//           icon
//           labelPosition="left"
//           primary
//           href="#/create"
//           className="controls"
//         >
//           <Icon name="database" />
//           Kurti naują klasę
//         </Button>
  
//         <Table selectable>
//           <Table.Header>
//             <Table.Row>
//               <Table.HeaderCell>Klasės pavadinimas</Table.HeaderCell>
//               <Table.HeaderCell>Pastatas</Table.HeaderCell>
//               <Table.HeaderCell>Aprašymas</Table.HeaderCell>
//               <Table.HeaderCell>Paskutinis atnaujinimas:</Table.HeaderCell>
//               <Table.HeaderCell>Veiksmai</Table.HeaderCell>
//             </Table.Row>
//           </Table.Header>
  
//           <Table.Body>
//             {rooms.map((room) => (
//               <Table.Row key={room.id}>
//                 <Table.Cell>{room.name}</Table.Cell>
//                 <Table.Cell>{room.building}</Table.Cell>
//                 <Table.Cell>{room.description}</Table.Cell>
//                 <Table.Cell>{room.modifiedDate}</Table.Cell>
//                 <Table.Cell collapsing>
//                   <Button
//                     basic
//                     primary
//                     compact
//                     icon="eye"
//                     title="Peržiūrėti"
//                     active={activeItem === room.id}
//                     onClick={setActive(room.id)}
//                   ></Button>
//                   <Button
//                     basic
//                     color="black"
//                     compact
//                     icon="trash alternate"
//                     onClick={() => removeRoom(room.id)}
//                   ></Button>
//                 </Table.Cell>
//               </Table.Row>
//             ))}
//           </Table.Body>
//         </Table>
//         <button onClick={()=> setActivePage(0)}> 1 </button>
//                 <button onClick={()=> setActivePage(1)}> 2 </button> 
//                 <button onClick={()=> setActivePage(2)}> 3 </button> 
//                 <button onClick={()=> setActivePage(3)}> 4 </button>      
//         {/* <Pagination 
//               defaultActivePage={1}
//               activePage={activePage}
//               onPageChange={onPageChange}
//               ellipsisItem={null}
//               siblingRange={1}
//               totalPages={10}          
              
//           />    */}  
//       </div>
//     );
//   }