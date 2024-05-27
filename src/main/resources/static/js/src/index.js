
@(document).ready(function (){

  function insertDataTest()
  {
    let table = $('#table');
    table.append('<tr id="tableinput">'
    +'<td>'
    +'<input placeholder="Input name">'
    +'</td>'
    +'<td>'
    +'<input placeholder="Input action">'
    +'</td>'
    +'<td>'
    +'<input placeholder="Input action name">'
    +'</td>'
    +'<td>'
    +'<input type="button" id="btn_send" class="btn btn-success" onclick="javascript:sendDataToSystem()" value="send"></input>'
    +'</td>'
    );
    table.remove('#tableinput');
  }

  function encodeQueryData(data) {
    const ret = [];
    for (let d in data)
    {
      ret.push(encodeURIComponent(d) + '=' + encodeURIComponent(data[d]));
      }
      return ret.join('&');
  }

  function getData()
  {
    $.ajax({
      //url: "?get=data&format=json",
      url: "",
      context: document.body
    }).done(function(data) {
      // Manage header
      $('#h1').empty();
      $('#h1').append('<h1 style="font-size:50px;"><marquee>User view</marquee></h1></p>');
      let table = $('#table');
      table.remove();
      table = $('<table>');
      table.addClass("table");
      table.addClass("table-striped");
      table.addClass("table-hover");
      table.attr('id', 'table');
      table.append('<thead class="thead-dark">'
        +'<tr>'
        +'<th style="max-width:200px;">#</th>'
        +'<th style="max-width:200px;">Vorname</th>'
        +'<th style="max-width:200px;">Nachname</th>'
        +'<th></th>'
        +'</tr>'
        +'</thead>'
      );
      table.append('<tr>');
      // Does not work with separated cmds, only inline like above :/
      table.append('<tbody>');
      let tabledata = '';
      for(let i=0; i<data.length; i++){
        // Does not work with separated cmds, only inline like above :/
        tabledata += '<tr>';
        tabledata += '<td>'+data[i].vorname+'</td>';
        tabledata += '<td>'+data[i].nachname+'</td>';
        tabledata += '</tr>';
      }
      table.append(tabledata);

      table.append('<td><input style="max-width:200px;" placeholder="Input vorname" id="input_vorname"></input></td>');
      table.append('<td><input style="max-width:200px;" placeholder="Input nachname" id="input_nachname"></input></td>');
      table.append(
        '<td>'
        +'<input type="button" id="btn_send" class="btn btn-success" onclick="javascript:sendDataToSystem()" value="send"></input>'
        +'</td>'
      );

      table.append('</tbody>');
      table.append('</table>');
      $('body').append('<p></p>');
      $('body').append(table);
    });
  }
  function insertData()
  {
    $.ajax({
      url: "?get=insert&format=json",
      context: document.body
    }).done(function(data) {
      alert("Inserted new data.");
    }).fail(function ( jqXHR, textStatus, errorThrown ) {
      alert('Failed');
    });;
  }
  function sendDataToSystem()
  {
    let input_name_position_value = $('#input_position').val();
    let input_name_value = $('#input_name').val();
    let input_action_value = $('#input_action').val();
    let input_action_name_value = $('#input_action_name').val();
    if(input_name_position_value && input_name_value == "" && input_action_value == "" && input_action_name_value == "")
    {}
    else {
      $.ajax({
        url: "?get=add_user"
        +"&format=json"
        +"&vorname="+input_name_value
        +"&nachname="+input_name_value,
        context: document.body
      }).done(function(data) {
        alert("Inserted "
        +input_name_value+" ",
        +"' to system.");
        getData();
      }).fail(function ( jqXHR, textStatus, errorThrown ) {
        console.log(textStatus);
        alert('Failed');
      });
    }
}
$(function() {
  $('#table').on('editable-save.bs.table', function(e, field, row, oldValue){
      console.log("1 "+ field);
      console.log("2 "+ row[field]);
      console.log("3 "+ row.lot);
      console.log("4 "+ oldValue);
  });    
});
})
