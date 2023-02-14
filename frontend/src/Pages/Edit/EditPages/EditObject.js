import React, { useEffect, useState } from 'react'
import { useParams } from 'react-router-dom';
import { Button, Input, Select, Table } from 'semantic-ui-react';
import './EditObject.css';


const JSON_HEADERS = {
    'Content-Type': 'application/json'
};


export function EditObject(props) {


    const [active, setActive] = useState()

    const yearOptions = [
        { key: 23, value: 2023, text: '2023' },
        { key: 24, value: 2024, text: '2024' },
        { key: 25, value: 2025, text: '2025' },
        { key: 26, value: 2026, text: '2026' },
        { key: 27, value: 2027, text: '2027' },
        { key: 28, value: 2028, text: '2028' },        
    ]

    const shiftOptions = [
        { key: 'r', value: 'morning', text: 'Rytas' },        
        { key: 'v', value: 'evening', text: 'Vakaras' },        

    ]

    const [error, setError] = useState();


    const [groups, setGroups] = useState({
        name: '',
        schoolYear: '',
        studentAmount: '',
        program: '',
        shift: '',
        modifiedDate: '',
    });

    useEffect(() => {
        fetch('/api/v1/groups/' + props.id)
            .then(response => response.json())
            .then(setGroups);
    }, [props]);


    const updateGroups = () => {
        fetch('/api/v1/groups/' + props.id, {
            method: 'PATCH',
            headers: JSON_HEADERS,
            body: JSON.stringify(groups)
        }).then(result => {
            if (!result.ok) {
                setError('Update failed');
            } else {
                setError();
            }
        })
    };

    const updateProperty = (property, event) => {
        setGroups({
            ...groups,
            [property]: event.target.value
        });
    };


    // const removeGroup = (id) => {
    //     fetch('/api/v1/groups/' + params.id, {
    //         method: 'DELETE',
    //         headers: JSON_HEADERS
    //     })
    //     .then(() => window.location = listUrl);
    // }



    return (<div>
        <div className='' id='table'>

            <Table celled compact color='violet'>
                <Table.Header >
                    <Table.Row  >
                        <Table.HeaderCell >Grupės pavadinimas "Teams"</Table.HeaderCell>
                        <Table.HeaderCell>Mokslo metai</Table.HeaderCell>
                        <Table.HeaderCell>Studentų skaičius</Table.HeaderCell>
                        <Table.HeaderCell>Programa</Table.HeaderCell>
                        <Table.HeaderCell>Pamaina</Table.HeaderCell>
                        <Table.HeaderCell>Paskutinis atnaujinimas:</Table.HeaderCell>
                        <Table.HeaderCell>Veiksmai</Table.HeaderCell>

                    </Table.Row>
                </Table.Header>

                <Table.Body>
                    <Table.Row  >
                        <Table.Cell collapsing><Input value={groups.name} onChange={(e) => updateProperty('name', e)} />
                        </Table.Cell>
                        <Table.Cell collapsing><Select disabled placeholder={groups.schoolYear} options={yearOptions} value={groups.schoolYear} onChange={(e) => updateProperty('schoolYear', e)} />
                        </Table.Cell>
                        <Table.Cell collapsing><Input disabled value={groups.studentAmount} onChange={(e) => updateProperty('studentAmount', e)} />
                        </Table.Cell>
                        <Table.Cell collapsing><Select options={shiftOptions} placeholder={groups.program} disabled value={groups.program} onChange={(e) => updateProperty('program', e)} />
                        </Table.Cell>
                        <Table.Cell collapsing><Select disabled options={shiftOptions} placeholder={groups.shift} value={groups.shift} onChange={(e) => updateProperty('shift', e)} />
                        </Table.Cell>

                        <Table.Cell > {groups.modifiedDate}  </Table.Cell>

                        <Table.Cell collapsing ><Button>Taisyti</Button> <Button primary onClick={updateGroups}>Save</Button>
                        </Table.Cell>


                    </Table.Row>

                </ Table.Body >
            </Table>
        </div>




    </div>
    )
}

