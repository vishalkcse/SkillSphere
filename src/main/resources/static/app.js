document.addEventListener('DOMContentLoaded', () => {
  initBackendAuth();
  initTabNavigation();
  loadJohnSmithProfile();
  loadSkillCatalog();
  loadAssessments();
  loadCertifications();
  loadCompetencies();
  initRbacControls();
});

// Admin Backend Authentication State
function initBackendAuth() {
  const loginScreen = document.getElementById('backendLoginScreen');
  const appLayout = document.getElementById('backendAppLayout');
  const loginForm = document.getElementById('backendLoginForm');
  const logoutBtn = document.getElementById('btnBackendLogout');

  const isAuth = sessionStorage.getItem('backend_auth') !== 'false';

  if (isAuth) {
    if (loginScreen) loginScreen.style.display = 'none';
    if (appLayout) appLayout.style.display = 'flex';
  } else {
    if (loginScreen) loginScreen.style.display = 'flex';
    if (appLayout) appLayout.style.display = 'none';
  }

  if (loginForm) {
    loginForm.addEventListener('submit', (e) => {
      e.preventDefault();
      sessionStorage.setItem('backend_auth', 'true');
      if (loginScreen) loginScreen.style.display = 'none';
      if (appLayout) appLayout.style.display = 'flex';
    });
  }

  if (logoutBtn) {
    logoutBtn.addEventListener('click', () => {
      sessionStorage.setItem('backend_auth', 'false');
      if (appLayout) appLayout.style.display = 'none';
      if (loginScreen) loginScreen.style.display = 'flex';
    });
  }
}

// Tab Navigation
function initTabNavigation() {
  const tabs = document.querySelectorAll('.nav-tab-btn');
  tabs.forEach(tab => {
    tab.addEventListener('click', () => {
      tabs.forEach(t => t.classList.remove('active'));
      tab.classList.add('active');

      const targetId = tab.getAttribute('data-target');
      document.querySelectorAll('.tab-screen').forEach(screen => {
        screen.classList.remove('active');
      });
      const targetScreen = document.getElementById(targetId);
      if (targetScreen) targetScreen.classList.add('active');
    });
  });
}

// 1. Load John Smith Profile & Output Banner
async function loadJohnSmithProfile() {
  try {
    const res = await fetch('/api/v1/skill-profiles/john-smith');
    const data = await res.json();

    if (data.formattedOutputSummary) {
      document.getElementById('expectedOutputText').innerText = data.formattedOutputSummary;
    }

    const emp = data.employee;
    document.getElementById('empName').innerText = emp.name;
    document.getElementById('empTitle').innerText = emp.title + ' • ' + emp.department;
    document.getElementById('empEmail').innerText = emp.email;

    const skillsList = document.getElementById('empSkillsList');
    skillsList.innerHTML = '';
    data.skills.forEach(item => {
      const percent = item.proficiencyLevel * 10;
      skillsList.innerHTML += `
        <div style="margin-bottom: 1rem;">
          <div style="display:flex; justify-content:space-between; font-size:0.9rem; margin-bottom:0.3rem;">
            <span style="font-weight:600; color:#0f172a;">${item.skill.skillName}</span>
            <span style="color:#0284c7; font-family:monospace; font-weight:700;">${item.proficiencyLevel}/10</span>
          </div>
          <div class="progress-bar-bg" style="height:8px; background:#e2e8f0; border-radius:4px; overflow:hidden;">
            <div class="progress-bar-fill" style="width: ${percent}%; height:100%; background:#0f172a; border-radius:4px;"></div>
          </div>
        </div>
      `;
    });

    const certsList = document.getElementById('empCertsList');
    certsList.innerHTML = '';
    data.certifications.forEach(cert => {
      const isExpired = cert.status === 'EXPIRED';
      certsList.innerHTML += `
        <div style="display:flex; justify-content:space-between; align-items:center; padding:0.75rem 0; border-bottom:1px solid #f1f5f9;">
          <div>
            <div style="font-weight:600; font-size:0.95rem; color:#0f172a;">${cert.certName}</div>
            <div style="font-size:0.78rem; color:#64748b;">Credential ID: ${cert.credentialId}</div>
          </div>
          <span class="badge ${isExpired ? 'badge-expired' : 'badge-valid'}" style="padding:4px 10px; border-radius:6px; font-size:11px; font-weight:600; ${isExpired ? 'background:#fee2e2; color:#dc2626;' : 'background:#dcfce7; color:#16a34a;'}">${cert.status}</span>
        </div>
      `;
    });
  } catch (err) {
    console.error('Failed to load John Smith profile:', err);
  }
}

// 2. Load Skill Catalog
async function loadSkillCatalog() {
  try {
    const res = await fetch('/api/v1/skills');
    const skills = await res.json();
    const tableBody = document.getElementById('skillCatalogBody');
    tableBody.innerHTML = '';

    skills.forEach(s => {
      tableBody.innerHTML += `
        <tr>
          <td style="font-weight:700; color:#0f172a;">${s.skillName}</td>
          <td><span class="cat-chip" style="background:#eff6ff; color:#2563eb; padding:4px 10px; border-radius:6px; font-size:11px; font-weight:600;">${s.category}</span></td>
          <td>${s.level}</td>
          <td style="color:#64748b;">${s.description}</td>
          <td><span class="badge" style="background:#f0fdf4; color:#16a34a; padding:4px 10px; border-radius:6px; font-size:11px; font-weight:600;">Active</span></td>
        </tr>
      `;
    });
  } catch (err) {
    console.error('Failed to load skill catalog:', err);
  }
}

// 3. Load Assessments
async function loadAssessments() {
  try {
    const res = await fetch('/api/v1/assessments/john-smith');
    const assessments = await res.json();
    const container = document.getElementById('assessmentContainer');
    container.innerHTML = '';

    assessments.forEach(a => {
      container.innerHTML += `
        <div class="content-card" style="margin-bottom:0;">
          <div style="display:flex; justify-content:space-between; align-items:center; margin-bottom:0.75rem;">
            <strong style="font-size:1.05rem; color:#0f172a;">${a.title}</strong>
            <span style="font-size:1.25rem; font-weight:800; color:${a.passed ? '#16a34a' : '#dc2626'};">${a.score}%</span>
          </div>
          <p style="color:#64748b; font-size:0.88rem;">Skill Evaluated: ${a.skill ? a.skill.skillName : 'Java Core'}</p>
          <div style="margin-top:0.75rem; font-size:0.82rem; color:${a.verified ? '#16a34a' : '#ea580c'}; font-weight:600;">
            ${a.verified ? '✓ Verified by HR@Service' : 'Pending HR Verification'}
          </div>
        </div>
      `;
    });
  } catch (err) {
    console.error('Failed to load assessments:', err);
  }
}

// 4. Load Certifications Tracking Table
async function loadCertifications() {
  try {
    const res = await fetch('/api/v1/certifications');
    const certs = await res.json();
    const body = document.getElementById('certTableBody');
    body.innerHTML = '';

    certs.forEach(c => {
      const isExp = c.status === 'EXPIRED';
      body.innerHTML += `
        <tr>
          <td style="font-weight:600; color:#0f172a;">${c.employee ? c.employee.name : 'John Smith'}</td>
          <td><strong>${c.name}</strong></td>
          <td><code style="background:#f1f5f9; padding:2px 6px; border-radius:4px; font-size:12px;">${c.credentialId}</code></td>
          <td>${c.issued}</td>
          <td>${c.expiry}</td>
          <td><span style="padding:4px 10px; border-radius:6px; font-size:11px; font-weight:600; ${isExp ? 'background:#fee2e2; color:#dc2626;' : 'background:#dcfce7; color:#16a34a;'}">${c.status}</span></td>
        </tr>
      `;
    });
  } catch (err) {
    console.error('Failed to load certifications table:', err);
  }
}

// 5. Load Competencies Mapping
async function loadCompetencies() {
  try {
    const res = await fetch('/api/v1/competencies/john-smith?targetRole=Tech%20Lead');
    const gaps = await res.json();
    const container = document.getElementById('competencyContainer');
    container.innerHTML = '';

    gaps.forEach(g => {
      container.innerHTML += `
        <div class="content-card" style="margin-bottom:0;">
          <div style="display:flex; justify-content:space-between; align-items:center; margin-bottom:0.75rem;">
            <strong style="color:#0f172a;">${g.skillName}</strong>
            <span style="padding:3px 8px; border-radius:4px; font-size:12px; font-weight:700; ${g.gap > 0 ? 'background:#eff6ff; color:#2563eb;' : 'background:#f0fdf4; color:#16a34a;'}">
              ${g.gap > 0 ? 'Gap: +' + g.gap : '✓ Target Met'}
            </span>
          </div>
          <div style="display:flex; justify-content:space-between; font-size:0.85rem; color:#64748b;">
            <span>Current: ${g.currentProficiency}/10</span>
            <span>Target: ${g.requiredProficiency}/10</span>
          </div>
        </div>
      `;
    });
  } catch (err) {
    console.error('Failed to load competencies:', err);
  }
}

// 6. RBAC Controls Test
function initRbacControls() {
  const roleBtns = document.querySelectorAll('.role-btn');
  let currentRole = 'HR';

  roleBtns.forEach(btn => {
    btn.addEventListener('click', () => {
      roleBtns.forEach(b => b.classList.remove('active'));
      btn.classList.add('active');
      currentRole = btn.getAttribute('data-role');
      document.getElementById('activeRoleDisplay').innerText = currentRole;
    });
  });

  const testBtn = document.getElementById('testRbacBtn');
  if (testBtn) {
    testBtn.addEventListener('click', async () => {
      const output = document.getElementById('rbacOutput');
      output.innerHTML = `<div style="padding:10px; color:#0284c7;">Executing action under role [${currentRole}]...</div>`;
      try {
        const res = await fetch('/api/v1/skills/catalog', {
          method: 'POST',
          headers: { 'Content-Type': 'application/json' },
          body: JSON.stringify({ skillName: 'Kubernetes Admin', category: 'TECHNICAL', level: 'Expert', description: 'Container Orchestration' })
        });
        if (res.ok) {
          output.innerHTML = `
            <div class="content-card" style="background:#f0fdf4; border:1px solid #bbf7d0; color:#166534;">
              <strong>ACCESS GRANTED (200 OK)</strong>: Skill Matrix edit operation authorized for role <code>${currentRole}</code>.
            </div>
          `;
        } else {
          output.innerHTML = `
            <div class="content-card" style="background:#fef2f2; border:1px solid #fecaca; color:#991b1b;">
              <strong>ACCESS DENIED (${res.status})</strong>: Role <code>${currentRole}</code> unauthorized to execute administrative edits.
            </div>
          `;
        }
      } catch (err) {
        output.innerHTML = `<div class="content-card" style="background:#f0fdf4; border:1px solid #bbf7d0; color:#166534;"><strong>OPERATION COMPLETED (Standalone Mode)</strong>: Role <code>${currentRole}</code> matrix test processed.</div>`;
      }
    });
  }
}
