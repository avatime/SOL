import { Typography } from "@mui/material";
import Box from "@mui/material/Box";
import EmojiIcon from "@mui/icons-material/SentimentDissatisfied";

function ExpirationTokenPage() {
  return (
    <Box display="flex" flexDirection="column" alignItems="center">
      <Box mt={10}>
        <EmojiIcon style={{"fontSize": "200px"}}/>
      </Box>

      <Typography variant="h5" component="h5">
        토큰이 만료됐습니다
      </Typography>
    </Box>
  );
}

export default ExpirationTokenPage;
